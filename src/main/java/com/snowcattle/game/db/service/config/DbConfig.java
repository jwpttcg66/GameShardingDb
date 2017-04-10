package com.snowcattle.game.db.service.config;

import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/4/6.
 * db的配置
 */
@Service
public class DbConfig {

    /**
     * db集群的编号
     */
    private int dbId;

    /**
     * 异步执行存储的线程大小
     */
    private int asyncDbOperationWorkerSize;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public int getAsyncDbOperationWorkerSize() {
        return asyncDbOperationWorkerSize;
    }

    public void setAsyncDbOperationWorkerSize(int asyncDbOperationWorkerSize) {
        this.asyncDbOperationWorkerSize = asyncDbOperationWorkerSize;
    }
}
