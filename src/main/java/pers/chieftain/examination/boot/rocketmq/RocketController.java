package pers.chieftain.examination.boot.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mq")
public class RocketController {

    @Autowired
    private RocketAttrs rocketAttrs;

    @Autowired
    private DefaultMQProducer concurrentlyProducer;
    @Autowired
    private DefaultMQProducer orderlyProducer;

    @GetMapping("/openAccountMsg")
    public SendResult openAccountMsg(String msgInfo) {
        // 可以不使用Config中的Group
        this.concurrentlyProducer.setProducerGroup(rocketAttrs.getProducer().getGroupName());
        SendResult sendResult = null;
        try {
            Message sendMsg = new Message(rocketAttrs.getProducer().getTopic(),
                    rocketAttrs.getProducer().getTag(),
                                         "open_account_key", msgInfo.getBytes());
            sendResult = this.concurrentlyProducer.send(sendMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendResult ;
    }

    @GetMapping("/sendOrderly")
    public SendResult sendOrderly(String msgInfo) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String shardingKey = UUID.randomUUID().toString();
        Message msg = new Message(rocketAttrs.getProducer().getTopic() + "Orderly", rocketAttrs.getProducer().getTag(), "KEY" + shardingKey, msgInfo.getBytes(StandardCharsets.UTF_8));
        //确保同一个订单号的数据放到同一个queue中
        SendResult sendResult = this.orderlyProducer.send(msg, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg,
                                       Object shardingKey) {
                int select = Math.abs(shardingKey.hashCode());
                if (select < 0) {
                    select = 0;
                }
                return mqs.get(select % mqs.size());
            }
        }, shardingKey);
        return sendResult;
    }
}
