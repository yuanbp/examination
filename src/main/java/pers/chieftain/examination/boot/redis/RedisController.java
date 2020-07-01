package pers.chieftain.examination.boot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chieftain
 * @date 2020/6/19 13:41
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/publish")
    public String publish() {
        stringRedisTemplate.convertAndSend("chat", "Hello from Redis!");
        stringRedisTemplate.convertAndSend("chat2", "Hello from Redis!2");
        return "ok";
    }
}
