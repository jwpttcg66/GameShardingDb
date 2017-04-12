package com.snowcattle.game.db.service.async.thread;

import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.service.config.DbConfig;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.util.ExecutorUtil;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/4/10.
 * 异步执行更新中心
 *  这个类采用模版编程
 */
@Service
public class AsyncDbOperation<T extends EntityService> extends TimerTask {

    @Autowired
    private RedisService redisService;

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

    }

    //获取模版参数类
    public Class<T> getEntityTClass(){
        Class classes = getClass();
        Class result = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return result;
    }
}
