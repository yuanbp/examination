package pers.chieftain.examination.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Map;

/**
 * @author chieftain
 * @date 2020/4/29 10:22
 */
public class RedissonTest {

    static RedissonClient client;

    static {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        client = Redisson.create(config);
    }

    public static void main(String[] args) {
        Map<String, String> testMap = client.getMap("testMap");
        testMap.put("1", "1");
    }
}
