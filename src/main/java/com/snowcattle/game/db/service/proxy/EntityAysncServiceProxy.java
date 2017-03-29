package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.cache.redis.RedisService;
import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.service.entity.EntityService;
import org.slf4j.Logger;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by jiangwenping on 17/3/29.
 * 存储策略为全部存入缓存(包括删除)，然后存入队列，进行异步线程存入db
 */
public class EntityAysncServiceProxy<T extends EntityService>  implements MethodInterceptor {
    private static final Logger proxyLogger = Loggers.dbServiceProxy;

    private RedisService redisService;

    public EntityAysncServiceProxy(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        return result;
    }
}
