package pers.chieftain.examination.boot.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ 生产者配置
 */
@Configuration
public class ProducerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerConfig.class);

    @Autowired
    private RocketAttrs rocketAttrs;

    @Bean
    public DefaultMQProducer concurrentlyProducer() {
        DefaultMQProducer producer;
        producer = new DefaultMQProducer(rocketAttrs.getProducer().getGroupName());
        producer.setNamesrvAddr(rocketAttrs.getNameSrvAddr());
        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        if (rocketAttrs.getProducer().getMaxMessageSize() != null) {
            producer.setMaxMessageSize(rocketAttrs.getProducer().getMaxMessageSize());
        }
        if (rocketAttrs.getProducer().getSendMsgTimeout() != null) {
            producer.setSendMsgTimeout(rocketAttrs.getProducer().getSendMsgTimeout());
        }
        //如果发送消息失败，设置重试次数，默认为2次
        if (rocketAttrs.getProducer().getRetryTimesWhenSendFailed() != null) {
            producer.setRetryTimesWhenSendFailed(rocketAttrs.getProducer().getRetryTimesWhenSendFailed());
        }
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }

    @Bean
    public DefaultMQProducer orderlyProducer() {
        DefaultMQProducer producer;
        producer = new DefaultMQProducer(rocketAttrs.getProducer().getGroupName() + "Orderly");
        producer.setNamesrvAddr(rocketAttrs.getNameSrvAddr());
        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        if (rocketAttrs.getProducer().getMaxMessageSize() != null) {
            producer.setMaxMessageSize(rocketAttrs.getProducer().getMaxMessageSize());
        }
        if (rocketAttrs.getProducer().getSendMsgTimeout() != null) {
            producer.setSendMsgTimeout(rocketAttrs.getProducer().getSendMsgTimeout());
        }
        //如果发送消息失败，设置重试次数，默认为2次
        if (rocketAttrs.getProducer().getRetryTimesWhenSendFailed() != null) {
            producer.setRetryTimesWhenSendFailed(rocketAttrs.getProducer().getRetryTimesWhenSendFailed());
        }
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }
}
