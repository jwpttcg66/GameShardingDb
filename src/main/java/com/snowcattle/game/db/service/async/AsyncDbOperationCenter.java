package com.snowcattle.game.db.service.async;

import com.redis.transaction.service.RGTRedisService;
import com.redis.transaction.service.TransactionService;
import com.snowcattle.game.db.service.async.thread.AsyncDbOperation;
import com.snowcattle.game.db.service.config.DbConfig;
import com.snowcattle.game.db.service.entity.AsyncOperationRegistry;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.util.ExecutorUtil;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by jwp on 2017/4/12.
 */
@Service
public class AsyncDbOperationCenter {

    /**
     * 执行db落得第线程数量
     */
    private NonOrderedQueuePoolExecutor operationExecutor;

    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private DbConfig dbConfig;

    @Autowired
    private AsyncOperationRegistry asyncOperationRegistry;

    /**
     * 事务redis服务
     */
    @Autowired
    private RGTRedisService rgtRedisService;

    /**
     * 事务服务
     */
    @Autowired
    private TransactionService transactionService;

    public void start() throws Exception {
        int coreSize =  dbConfig.getAsyncDbOperationSaveWorkerSize();
        operationExecutor = new NonOrderedQueuePoolExecutor(coreSize);
        int selectSize = dbConfig.getAsyncDbOperationSaveWorkerSize();
        scheduledExecutorService = Executors.newScheduledThreadPool(selectSize);

        //开始调度线程
        asyncOperationRegistry.startup();

        Collection<Class> collection = asyncOperationRegistry.getAllEntityServiceRegistry();
        for(Class classes: collection){
            Object object = classes.newInstance();
            AsyncDbOperation asyncDbOperation = (AsyncDbOperation) object;
            scheduledExecutorService.scheduleAtFixedRate(asyncDbOperation, 0, 60, TimeUnit.SECONDS);
        }
    }

    public void stop()throws Exception {
        if(operationExecutor != null){
            ExecutorUtil.shutdownAndAwaitTermination(operationExecutor, 60, TimeUnit.SECONDS);
        }
        if(scheduledExecutorService != null){
            ExecutorUtil.shutdownAndAwaitTermination(scheduledExecutorService, 60, TimeUnit.SECONDS);
        }
    }

    public RGTRedisService getRgtRedisService() {
        return rgtRedisService;
    }

    public void setRgtRedisService(RGTRedisService rgtRedisService) {
        this.rgtRedisService = rgtRedisService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
