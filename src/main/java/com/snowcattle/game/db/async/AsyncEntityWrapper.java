package com.snowcattle.game.db.async;

import com.snowcattle.game.db.entity.IEntity;

/**
 * Created by jiangwenping on 17/4/6.
 * 异步实体封装箱, 里面包含了实体的快照
 *  所有需要异步存储的实体，都会包装在这里，然后传递到异步队列里面
 */
public class AsyncEntityWrapper{

    /**
     * 包装时间
     */
    private long wrapperTime;

    /**
     * 包装实体
     */
    private IEntity entity;

    public AsyncEntityWrapper(IEntity entity) {
        this.entity = entity;
        this.wrapperTime = System.currentTimeMillis();
    }

    public long getWrapperTime() {
        return wrapperTime;
    }

    public void setWrapperTime(long wrapperTime) {
        this.wrapperTime = wrapperTime;
    }

    public IEntity getEntity() {
        return entity;
    }

    public void setEntity(IEntity entity) {
        this.entity = entity;
    }
}
