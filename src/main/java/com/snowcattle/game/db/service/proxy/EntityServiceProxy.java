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
 *
 * 存储策略为 insert的时候插入db,然后更新缓存。query的时候优先缓存，找不到的时候查询db,更新缓存。delete的时候删除db，删除缓存，
 * useRedisFlag 为是否使用缓存redis标志
 */
public class EntityServiceProxy<T extends EntityService>  implements MethodInterceptor {

    private static final Logger proxyLogger = Loggers.dbServiceProxy;

    private RedisService redisService;

    private boolean useRedisFlag;

    public EntityServiceProxy(RedisService redisService, boolean useRedisFlag) {
        this.redisService = redisService;
        this.useRedisFlag = useRedisFlag;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        DbOperation dbOperation = method.getAnnotation(DbOperation.class);
        if(dbOperation == null || !useRedisFlag) { //如果没有进行注解或者不使用redis，直接进行返回
            result = methodProxy.invokeSuper(obj, args);
        }else {
            //进行数据库操作
            DbOperationEnum dbOperationEnum = dbOperation.operation();
            switch (dbOperationEnum) {
                case insert:
                    result = methodProxy.invokeSuper(obj, args);
                    BaseEntity baseEntity = (BaseEntity) args[0];
                    updateAllFieldEntity(baseEntity);
                    break;
                case update:
                    result = methodProxy.invokeSuper(obj, args);
                    baseEntity = (BaseEntity) args[0];
                    updateChangedFieldEntity(baseEntity);
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
                    if (result == null) {
                        result = methodProxy.invokeSuper(obj, args);
                        if(result != null){
                            baseEntity = (BaseEntity) result;
                            updateAllFieldEntity(baseEntity);
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
                    if (result == null) {
                        result = methodProxy.invokeSuper(obj, args);
                        if(result != null){
                            List<BaseEntity> entityList = (List<BaseEntity>) result;
                            updateAllFieldEntityList(entityList);
                        }
                    }
                    break;
                case delete:
                    result = methodProxy.invokeSuper(obj, args);
                    baseEntity = (BaseEntity) args[0];
                    deleteEntity(baseEntity);
                    break;
                case insertBatch:
                    result = methodProxy.invokeSuper(obj, args);
                    List<BaseEntity> entityList = (List<BaseEntity>) args[0];
                    updateAllFieldEntityList(entityList);
                    break;
                case updateBatch:
                    result = methodProxy.invokeSuper(obj, args);
                    entityList = (List<BaseEntity>) args[0];
                    updateChangedFieldEntityList(entityList);
                    break;
                case deleteBatch:
                    result = methodProxy.invokeSuper(obj, args);
                    entityList = (List<BaseEntity>) args[0];
                    deleteEntityList(entityList);
                    break;
            }
        }
        return result;
    }

    /**
     * 更新变化字段
     * @param entity
     */
    protected void updateChangedFieldEntity(BaseEntity entity){
        if (entity != null) {
            if (entity instanceof RedisInterface) {
                RedisInterface redisInterface = (RedisInterface) entity;
                redisService.updateObjectHashMap(EntityUtils.getRedisKey(redisInterface), entity.getEntityProxyWrapper().getEntityProxy().getChangeParamSet());
            } else if (entity instanceof RedisListInterface) {
                RedisListInterface redisListInterface = (RedisListInterface) entity;
                List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                redisListInterfaceList.add(redisListInterface);
                redisService.setListToHash(EntityUtils.getRedisKey(redisListInterface), redisListInterfaceList);
            }
        }
    }

    /**
     * 更新所有字段
     * @param entity
     */
    protected void updateAllFieldEntity(BaseEntity entity){
        if (entity != null) {
            if (entity instanceof RedisInterface) {
                RedisInterface redisInterface = (RedisInterface) entity;
                redisService.setObjectToHash(EntityUtils.getRedisKey(redisInterface), entity);
            } else if (entity instanceof RedisListInterface) {
                RedisListInterface redisListInterface = (RedisListInterface) entity;
                List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                redisListInterfaceList.add(redisListInterface);
                redisService.setListToHash(EntityUtils.getRedisKey(redisListInterface), redisListInterfaceList);
            }
        }
    }

    /**
     * 删除实体
     * @param baseEntity
     */
    protected void deleteEntity(BaseEntity baseEntity){
        if (baseEntity != null) {
            if (baseEntity instanceof RedisInterface) {
                RedisInterface redisInterface = (RedisInterface) baseEntity;
                redisService.deleteKey(EntityUtils.getRedisKey(redisInterface));
            }else if(baseEntity instanceof RedisListInterface){
                RedisListInterface redisListInterface = (RedisListInterface) baseEntity;
                redisService.hdel(EntityUtils.getRedisKey(redisListInterface), redisListInterface.getSubUniqueKey());
            }
        }
    }

    /**
     * 更新所有字段实体列表
     * @param entityList
     */
    public void updateAllFieldEntityList(List<BaseEntity> entityList){
        //拿到第一个，看一下类型
        if(entityList.size() > 0){
            BaseEntity entity = entityList.get(0);
            if(entity instanceof  RedisInterface){
                for(BaseEntity baseEntity: entityList){
                    updateAllFieldEntity(entity);
                }
            }else if(entity instanceof RedisListInterface){
                List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                for(BaseEntity baseEntity: entityList){
                    redisListInterfaceList.add((RedisListInterface) baseEntity);
                }
                redisService.setListToHash(EntityUtils.getRedisKey((RedisInterface) entity), redisListInterfaceList);
            }
        }
    }

    /**
     * 更新变化字段实体列表
     * @param entityList
     */
    protected void updateChangedFieldEntityList(List<BaseEntity> entityList){
        if(entityList.size() > 0) {
            BaseEntity entity = entityList.get(0);
            if (entity != null) {
                if (entity instanceof RedisInterface) {
                    for(BaseEntity baseEntity: entityList){
                        updateChangedFieldEntity(entity);
                    }
                } else if (entity instanceof RedisListInterface) {

                    List<RedisListInterface> redisListInterfaceList = new ArrayList<>();
                    for(BaseEntity baseEntity: entityList) {
                        RedisListInterface redisListInterface = (RedisListInterface) baseEntity;
                        redisListInterfaceList.add(redisListInterface);
                    }
                    redisService.setListToHash(EntityUtils.getRedisKey((RedisListInterface) entity), redisListInterfaceList);
                }
            }
        }
    }

    //删除实体列表
    protected void deleteEntityList(List<BaseEntity> entityList){
        if(entityList.size() > 0) {
            BaseEntity entity = entityList.get(0);
            if (entity != null) {
                if (entity instanceof RedisInterface) {
                    for(BaseEntity baseEntity: entityList){
                        deleteEntity(baseEntity);
                    }
                } else if (entity instanceof RedisListInterface) {
                    List<String> redisListInterfaceList = new ArrayList<>();
                    for(BaseEntity baseEntity: entityList) {
                        RedisListInterface redisListInterface = (RedisListInterface) baseEntity;
                        redisListInterfaceList.add(redisListInterface.getSubUniqueKey());
                    }
                    redisService.hdel(EntityUtils.getRedisKey((RedisInterface) entity), redisListInterfaceList.toArray(new String[0]));
                }
            }
        }
    }
}
