package com.snowcattle.game.db.service.test.redis;

import com.snowcattle.game.db.cache.redis.RedisService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 17/4/7.
 */
public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        RedisService redisService = (RedisService) classPathXmlApplicationContext.getBean("redisService");
        String setKey = "set";
        String playerKey = "player";
        redisService.deleteKey(setKey);
        redisService.deleteKey(playerKey);
        RedisPop redisPop = new RedisPop(redisService, setKey, playerKey);
        RedisPush redisPush = new RedisPush(redisService, setKey, playerKey);
        redisPop.start();
        redisPush.start();
    }
}
