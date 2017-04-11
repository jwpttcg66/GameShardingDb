package com.snowcattle.game.db.service.async;

import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.service.config.DbConfig;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.util.ExecutorUtil;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/4/10.
 * 异步执行更新中心
 *  这个类采用模版编程
 */
@Service
public class AsyncDbOperation<T extends EntityService> {

    @Autowired
    private RedisService redisService;

    @Autowired
    private DbConfig dbConfig;

    /**
     * 执行db落得第线程数量
     */
    private NonOrderedQueuePoolExecutor operationExecutor;

    private ScheduledExecutorService scheduledExecutorService;


    public void start(){
        int coreSize =  dbConfig.getAsyncDbOperationSaveWorkerSize();
        operationExecutor = new NonOrderedQueuePoolExecutor(coreSize);
        int selectSize = dbConfig.getAsyncDbOperationSaveWorkerSize();
        scheduledExecutorService = Executors.newScheduledThreadPool(selectSize);

        //开始调度线程
    }

    public void stop(){
        if(operationExecutor != null){
            ExecutorUtil.shutdownAndAwaitTermination(operationExecutor, 60, TimeUnit.SECONDS);
        }
        if(scheduledExecutorService != null){
            ExecutorUtil.shutdownAndAwaitTermination(scheduledExecutorService, 60, TimeUnit.SECONDS);
        }
    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public NonOrderedQueuePoolExecutor getOperationExecutor() {
        return operationExecutor;
    }

    public void setOperationExecutor(NonOrderedQueuePoolExecutor operationExecutor) {
        this.operationExecutor = operationExecutor;
    }


    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    //获取模版参数类
    public Class<T> getEntityTClass(){
        Class classes = getClass();
        Class result = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return result;
    }
}
