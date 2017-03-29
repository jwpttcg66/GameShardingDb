package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.cache.redis.AsyncCacheKey;
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
import java.util.List;

/**
 * Created by jiangwenping on 17/3/29.
 * 存储策略为全部存入缓存(包括删除)，然后存入队列，进行异步线程存入db
 */
public class EntityAysncServiceProxy<T extends EntityService>  extends EntityServiceProxy implements MethodInterceptor {
    private static final Logger proxyLogger = Loggers.dbServiceProxy;

    private RedisService redisService;

    public EntityAysncServiceProxy(RedisService redisService) {
        super(redisService, false);
        this.redisService = redisService;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        DbOperation dbOperation = method.getAnnotation(DbOperation.class);
        if(dbOperation == null) { //如果没有进行注解，直接进行返回
            result = methodProxy.invokeSuper(obj, args);
        }else {
            //进行数据库操作
            DbOperationEnum dbOperationEnum = dbOperation.operation();
            switch (dbOperationEnum) {
                case insert:
                    BaseEntity baseEntity = (BaseEntity) args[0];
                    updateAllFieldEntity(baseEntity);
                    //放入异步存储的key
                    if(baseEntity instanceof AsyncCacheKey) {
                        AsyncCacheKey asyncCacheKeyEntity = (AsyncCacheKey) baseEntity;
                        if(baseEntity instanceof  RedisInterface) {
                            redisService.lpushString(asyncCacheKeyEntity.getAsyncCacheKey(), EntityUtils.getRedisKey((RedisInterface) baseEntity));
                        }else{
                            proxyLogger.error("async save interface not RedisInterface " + baseEntity.getClass().getSimpleName() + " use RedisListInterface " + baseEntity.toString());
                        }
                    }else{
                        proxyLogger.error("async save interface not asynccachekey " + baseEntity.getClass().getSimpleName() + " use " + baseEntity.toString());
                    }
                    break;
                case insertBatch:
                    List<BaseEntity> entityList = (List<BaseEntity>) args[0];
                    updateAllFieldEntityList(entityList);
                    break;
                case update:
                    baseEntity = (BaseEntity) args[0];
                    updateChangedFieldEntity(baseEntity);
                    break;
                case updateBatch:
                    entityList = (List<BaseEntity>) args[0];
                    updateChangedFieldEntityList(entityList);
                    break;
                case delete:
                    baseEntity = (BaseEntity) args[0];
                    deleteEntity(baseEntity);
                    break;
                case deleteBatch:
                    entityList = (List<BaseEntity>) args[0];
                    deleteEntityList(entityList);
                    break;
                case query:
                    baseEntity = (BaseEntity) args[0];
                    if (baseEntity != null) {
                        if (baseEntity instanceof RedisInterface) {
                            RedisInterface redisInterface = (RedisInterface) baseEntity;
                            result = redisService.getObjectFromHash(EntityUtils.getRedisKey(redisInterface), baseEntity.getClass());
                        } else {
                            proxyLogger.error("query interface RedisListInterface " + baseEntity.getClass().getSimpleName() + " use RedisInterface " + baseEntity.toString());
                        }
                    }
                    break;
                case queryList:
                    baseEntity = (BaseEntity) args[0];
                    if (baseEntity != null) {
                        if (baseEntity instanceof RedisListInterface) {
                            RedisInterface redisInterface = (RedisInterface) baseEntity;
                            result = redisService.getListFromHash(EntityUtils.getRedisKey(redisInterface), baseEntity.getClass());
                        } else {
                            proxyLogger.error("query interface RedisInterface " + baseEntity.getClass().getSimpleName() + " use RedisListInterface " + baseEntity.toString());
                        }
                    }
                    break;
            }
        }
        return result;
    }
}
