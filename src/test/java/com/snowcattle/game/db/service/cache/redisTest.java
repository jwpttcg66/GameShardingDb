package com.snowcattle.game.db.service.cache;

import com.snowcattle.game.db.cache.redis.RedisService;
import com.snowcattle.game.db.util.BeanUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class redisTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});
        RedisService redisService = (RedisService) BeanUtil.getBean("redisService");
        redisService.setString("test", "2");
        System.out.println(redisService.getString("test"));
    }
}
