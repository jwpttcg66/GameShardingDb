package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.cache.redis.RedisService;
import com.snowcattle.game.db.common.annotation.DbOperation;
import com.snowcattle.game.db.common.enums.DbOperationEnum;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.entity.EntityService;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by jwp on 2017/3/23.
 * 实体存储服务代理
 */
public class EntityServiceProxy<T extends EntityService>  implements MethodInterceptor {

    private RedisService redisService;

    public EntityServiceProxy(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        DbOperation dbOperation = method.getDeclaredAnnotation(DbOperation.class);
        if(dbOperation == null) {
            result = methodProxy.invokeSuper(obj, args);
        }else{
            //进行数据库操作
            DbOperationEnum dbOperationEnum = DbOperationEnum.valueOf(dbOperation.operation());
            if(dbOperationEnum.equals(DbOperationEnum.insert)){

            }
        }
        return result;
    }

}
