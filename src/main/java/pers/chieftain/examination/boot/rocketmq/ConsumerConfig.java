package pers.chieftain.examination.boot.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ 消费者配置
 */
@Configuration
public class ConsumerConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerConfig.class) ;

    @Autowired
    private RocketAttrs rocketAttrs;

    @Autowired
    private ConcurrentlyListener concurrentlyListener;
    @Autowired
    private OrderlyListener orderlyListener;

    @Bean
    public DefaultMQPushConsumer getConcurrentlyConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketAttrs.getProducer().getGroupName());
        consumer.setNamesrvAddr(rocketAttrs.getNameSrvAddr());
        consumer.setConsumeThreadMin(rocketAttrs.getConsumer().getConsumeThreadMin());
        consumer.setConsumeThreadMax(rocketAttrs.getConsumer().getConsumeThreadMax());
        consumer.registerMessageListener(concurrentlyListener);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setConsumeMessageBatchMaxSize(rocketAttrs.getConsumer().getConsumeMessageBatchMaxSize());
        try {
            String[] topicTagsArr = rocketAttrs.getConsumer().getTopics().split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("~");
                consumer.subscribe(topicTag[0],topicTag[1]);
            }
            consumer.start();
        }catch (MQClientException e){
            e.printStackTrace();
        }
        return consumer;
    }

    @Bean
    public DefaultMQPushConsumer getOrderlyConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketAttrs.getProducer().getGroupName() + "Orderly");
        consumer.setNamesrvAddr(rocketAttrs.getNameSrvAddr());
        consumer.setConsumeThreadMin(rocketAttrs.getConsumer().getConsumeThreadMin());
        consumer.setConsumeThreadMax(rocketAttrs.getConsumer().getConsumeThreadMax());
        consumer.registerMessageListener(orderlyListener);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setConsumeMessageBatchMaxSize(rocketAttrs.getConsumer().getConsumeMessageBatchMaxSize());
        try {
            String[] topicTagsArr = rocketAttrs.getConsumer().getTopics().split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("~");
                consumer.subscribe(topicTag[0] + "Orderly",topicTag[1]);
            }
            consumer.start();
        }catch (MQClientException e){
            e.printStackTrace();
        }
        return consumer;
    }
}
