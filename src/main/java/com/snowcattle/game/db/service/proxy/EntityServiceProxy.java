package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.common.annotation.DbOperation;
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
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        DbOperation dbOperation = method.getDeclaredAnnotation(DbOperation.class);
        if(dbOperation == null) {
            result = methodProxy.invokeSuper(obj, args);
        }else{
            //进行数据库操作

        }
        return result;
    }

}
