package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.cache.redis.RedisInterface;
import com.snowcattle.game.db.cache.redis.RedisListInterface;
import com.snowcattle.game.db.cache.redis.RedisService;
import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.common.annotation.DbOperation;
import com.snowcattle.game.db.common.enums.DbOperationEnum;
import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jwp on 2017/3/23.
 * 实体存储服务代理
 */
public class EntityServiceProxy<T extends EntityService>  implements MethodInterceptor {

    private static final Logger proxyLogger = Loggers.dbServiceProxy;
    private RedisService redisService;

    public EntityServiceProxy(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        DbOperation dbOperation = method.getAnnotation(DbOperation.class);
        if(dbOperation == null) {
            result = methodProxy.invokeSuper(obj, args);
        }else{
            //进行数据库操作,第一个参数默认都是mapper
            DbOperationEnum dbOperationEnum = DbOperationEnum.valueOf(dbOperation.operation());
            if(dbOperationEnum.equals(DbOperationEnum.insert)){
                result = methodProxy.invokeSuper(obj, args);
                BaseEntity baseEntity = (BaseEntity) args[0];
                if(baseEntity != null){
                    if(baseEntity instanceof  RedisInterface){
                        RedisInterface redisInterface = (RedisInterface) baseEntity;
                        redisService.setObjectToHash(EntityUtils.getRedisKey(redisInterface), baseEntity);
                    }else if(obj instanceof RedisListInterface){
                        RedisListInterface redisListInterface = (RedisListInterface) baseEntity;
                        List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                        redisListInterfaceList.add(redisListInterface);
                        redisService.setListToHash(EntityUtils.getRedisKey(redisListInterface), redisListInterfaceList);
                    }
                }

            }else if(dbOperationEnum.equals(DbOperationEnum.update)){
                result = methodProxy.invokeSuper(obj, args);
                BaseEntity baseEntity = (BaseEntity) args[0];
                if(baseEntity != null){
                    if(baseEntity instanceof RedisInterface){
                        RedisInterface redisInterface = (RedisInterface) baseEntity;
                        redisService.updateObjectHashMap(EntityUtils.getRedisKey(redisInterface), baseEntity.getEntityProxyWrapper().getEntityProxy().getChangeParamSet());
                    }else if(baseEntity instanceof RedisListInterface){
                        RedisListInterface redisListInterface = (RedisListInterface) baseEntity;
                        List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                        redisListInterfaceList.add(redisListInterface);
                        redisService.setListToHash(EntityUtils.getRedisKey(redisListInterface), redisListInterfaceList);
                    }
                }
            }else if(dbOperationEnum.equals(DbOperationEnum.query)){
                BaseEntity baseEntity = (BaseEntity) args[0];
                if(baseEntity != null){
                    if(baseEntity instanceof RedisInterface){
                        RedisInterface redisInterface = (RedisInterface) baseEntity;
                        result = redisService.getObjectFromHash(EntityUtils.getRedisKey(redisInterface), baseEntity.getClass());
                    }else{
                        proxyLogger.error("query interface RedisListInterface " + baseEntity.getClass().getSimpleName() + " use RedisInterface " + baseEntity.toString());
                    }
                }
                if(result == null) {
                    result = methodProxy.invokeSuper(obj, args);
                }
            }else if(dbOperationEnum.equals(DbOperationEnum.queryList)){

                BaseEntity baseEntity = (BaseEntity) args[0];
                if(baseEntity != null){
                    if(baseEntity instanceof RedisListInterface){
                        RedisInterface redisInterface = (RedisInterface) baseEntity;
                        result = redisService.getListFromHash(EntityUtils.getRedisKey(redisInterface), baseEntity.getClass());
                    }else{
                        proxyLogger.error("query interface RedisInterface " + baseEntity.getClass().getSimpleName() + " use RedisListInterface " + baseEntity.toString());
                    }
                }
                if(result == null) {
                    result = methodProxy.invokeSuper(obj, args);
                }
            }else if(dbOperationEnum.equals(DbOperationEnum.delete)){
                result = methodProxy.invokeSuper(obj, args);
                BaseEntity baseEntity = (BaseEntity) args[0];
                if(baseEntity != null){
                    if(baseEntity instanceof RedisInterface){
                        RedisInterface redisInterface = (RedisInterface) baseEntity;
                        redisService.deleteKey(EntityUtils.getRedisKey(redisInterface));
                    }
                }
            }
        }
        return result;
    }


}
