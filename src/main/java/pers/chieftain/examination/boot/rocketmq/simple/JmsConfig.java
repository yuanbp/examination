package pers.chieftain.examination.boot.rocketmq.simple;

public class JmsConfig {

    /**
     * 端口
     */
    public static final String NAME_SERVER = "192.168.2.135:9876;192.168.2.136:9876";

    /**
     * topic,消息依赖于topic
     */
    public static final String TOPIC = "examination";
}