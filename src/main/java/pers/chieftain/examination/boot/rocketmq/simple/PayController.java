package pers.chieftain.examination.boot.rocketmq.simple;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

/**
 * @author chieftain
 * @date 2020/6/17 18:36
 */
@RestController
@RequestMapping("/mq")
public class PayController {

    @Autowired
    private PayProducer payProducer;

    /**
     * 同步发送,等待发送结果
     */
    @RequestMapping("/api/v1/pay_cb")
    public Object callback(String text) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // 创建消息  主题   二级分类   消息内容好的字节数组
        Message message = new Message(JmsConfig.TOPIC, "taga", ("hello rocketMQ " + text).getBytes());

        SendResult send = payProducer.getProducer().send(message);

        System.out.println(send);

        return new HashMap<>();
    }

    /**
     * 异步发送,直接返回。在callback处理发送结果
     */
    public void sendAsync(String topic, String tags,String keys, String context) throws UnsupportedEncodingException, RemotingException, MQClientException, InterruptedException {
        Message message = new Message(topic, tags, keys, context.getBytes(RemotingHelper.DEFAULT_CHARSET));
        payProducer.getProducer().send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("Send message: " + message);
                System.out.println("Send Success: " + sendResult);
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("Send message: " + message);
                System.out.println("Send Exception: " + e);
            }
        });
    }

    /**
     * 顺序消息的原理：消息发往同一个队列
     * @param topic
     * @param tags
     * @param keys
     * @param context
     * @param arg
     * @return
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    public SendResult sendOrderly(String topic, String tags, String keys, String context, Object arg) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message(topic, tags, keys, context.getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult sendResult = payProducer.getProducer().send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }
        }, arg);

        return sendResult;
    }
}
