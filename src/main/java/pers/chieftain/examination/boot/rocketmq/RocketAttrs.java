package pers.chieftain.examination.boot.rocketmq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chieftain
 * @date 2020/6/18 10:53
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class RocketAttrs {

    private String nameSrvAddr;

    private ProducerAttrs producer;
    private ConsumerAttrs consumer;

    @Setter
    @Getter
    public static class ProducerAttrs {
        private String isOnOff;
        private String topic;
        private String groupName;
        private String tag;
        private Integer maxMessageSize ;
        private Integer sendMsgTimeout;
        private Integer retryTimesWhenSendFailed;
    }

    @Setter
    @Getter
    public static class ConsumerAttrs {
        private String isOnOff;
        private String groupName;
        private int consumeThreadMin;
        private int consumeThreadMax;
        private String topics;
        private int consumeMessageBatchMaxSize;
    }

}
