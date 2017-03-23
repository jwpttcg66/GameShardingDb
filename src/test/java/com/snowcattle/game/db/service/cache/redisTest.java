package com.snowcattle.game.db.service.cache;

import com.snowcattle.game.db.cache.redis.RedisService;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.proxy.EnityProxyService;
import com.snowcattle.game.db.util.BeanUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class redisTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});
        RedisService redisService = (RedisService) BeanUtil.getBean("redisService");
//        redisService.setString("test", "2");
//        System.out.println(redisService.getString("test"));
        Order order = new Order();
        long id = 2;
        long userId = 2;
        String status = "测试";
        order.setId(id);
        order.setUserId(userId);
        order.setStatus(status);
        order.setDeleted(false);
        String key = "test";
        redisService.setObjectToHash(key, order);
        Order queryOrder= redisService.getObjectFromHash(key, Order.class);
        System.out.println(queryOrder);

        EnityProxyService enityProxyService = new EnityProxyService();
        Order proxyOrder = enityProxyService.createProxyEntity(queryOrder);
        proxyOrder.setStatus("test");
        redisService.updateObjectHashMap(key, proxyOrder.getEntityProxyWrapper().getEntityProxy().getChangeParamSet());
        queryOrder= redisService.getObjectFromHash(key, Order.class);
        System.out.println(queryOrder);
    }
}
