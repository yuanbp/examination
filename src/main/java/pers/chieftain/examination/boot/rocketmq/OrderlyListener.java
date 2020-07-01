package pers.chieftain.examination.boot.rocketmq;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 消息消费监听
 */
@Component
public class OrderlyListener implements MessageListenerOrderly {
    private static final Logger LOG = LoggerFactory.getLogger(OrderlyListener.class) ;

    @Autowired
    private RocketAttrs rocketAttrs;

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext context) {
        context.setAutoCommit(true);
        if (CollectionUtils.isEmpty(list)){
            return ConsumeOrderlyStatus.SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        LOG.info("接受到的消息为："+new String(messageExt.getBody()));
        int reConsume = messageExt.getReconsumeTimes();
        // 消息已经重试了3次，如果不需要再次消费，则返回成功
        if(reConsume ==3){
            return ConsumeOrderlyStatus.SUCCESS;
        }
        if(messageExt.getTopic().equals(rocketAttrs.getProducer().getTopic())){
            String tags = messageExt.getTags() ;
            switch (tags){
                case "rocketTag":
                    LOG.info("开户 tag == >>"+tags);
                    break ;
                default:
                    LOG.info("未匹配到Tag == >>"+tags);
                    break;
            }
        }
        // 消息消费成功
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
