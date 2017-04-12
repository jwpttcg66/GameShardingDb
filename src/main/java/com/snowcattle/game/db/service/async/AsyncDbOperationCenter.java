package com.snowcattle.game.db.service.async;

import com.snowcattle.game.db.service.config.DbConfig;
import com.snowcattle.game.db.util.ExecutorUtil;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
