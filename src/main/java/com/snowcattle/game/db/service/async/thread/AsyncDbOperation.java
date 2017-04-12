package com.snowcattle.game.db.service.async.thread;

import com.snowcattle.game.db.service.async.transaction.factory.DbGameTransactionEntityFactory;
import com.snowcattle.game.db.service.async.transaction.factory.DbGameTransactionEntityCauseFactory;
import com.snowcattle.game.db.service.redis.AsyncRedisKeyEnum;
import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.sharding.EntityServiceShardingStrategy;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.TimerTask;

/**
 * Created by jiangwenping on 17/4/10.
 * 异步执行更新中心
 *  这个类采用模版编程
 */
@Service
public abstract class AsyncDbOperation<T extends EntityService> extends TimerTask {

    /**
     * db里面的redis服务
     */
    @Autowired
    private RedisService redisService;

    @Autowired
    private DbGameTransactionEntityFactory dbGameTransactionEntityFactory;

    @Autowired
    private DbGameTransactionEntityCauseFactory dbGameTransactionEntityCauseFactory;

    /**
     * 执行db落得第线程数量
     */
    private NonOrderedQueuePoolExecutor operationExecutor;

    public NonOrderedQueuePoolExecutor getOperationExecutor() {
        return operationExecutor;
    }

    public void setOperationExecutor(NonOrderedQueuePoolExecutor operationExecutor) {
        this.operationExecutor = operationExecutor;
    }

    @Override
    public void run() {
        EntityService entityService = getWrapperEntityService();
        EntityServiceShardingStrategy entityServiceShardingStrategy = entityService.getEntityServiceShardingStrategy();
        int size = entityServiceShardingStrategy.getDbCount();
        for(int i = 0; i < size; i++){
            saveDb(i, entityService);
        }
    }

    /**
     * 存储db
     * @param dbId
     * @param entityService
     */
    public void saveDb(int dbId, EntityService entityService){
        String simpleClassName = entityService.getEntityTClass().getSimpleName();
        String dbRedisKey = AsyncRedisKeyEnum.ASYNC_DB.getKey() + dbId + "#" + entityService.getEntityTClass().getSimpleName();
        long saveSize = redisService.scardString(dbRedisKey);
        for(long k = 0; k < saveSize; k++){
            String playerKey = redisService.spopString(dbRedisKey);
            if(StringUtils.isEmpty(playerKey)){
                break;
            }
            //查找玩家数据进行存储 进行redis-game-transaction 加锁


        }
    }

    //获取模版参数类
    public Class<T> getEntityTClass(){
        Class classes = getClass();
        Class result = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return result;
    }

    public abstract EntityService getWrapperEntityService();

    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public DbGameTransactionEntityFactory getDbGameTransactionEntityFactory() {
        return dbGameTransactionEntityFactory;
    }

    public void setDbGameTransactionEntityFactory(DbGameTransactionEntityFactory dbGameTransactionEntityFactory) {
        this.dbGameTransactionEntityFactory = dbGameTransactionEntityFactory;
    }

    public DbGameTransactionEntityCauseFactory getDbGameTransactionEntityCauseFactory() {
        return dbGameTransactionEntityCauseFactory;
    }

    public void setDbGameTransactionEntityCauseFactory(DbGameTransactionEntityCauseFactory dbGameTransactionEntityCauseFactory) {
        this.dbGameTransactionEntityCauseFactory = dbGameTransactionEntityCauseFactory;
    }
}
