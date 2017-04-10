package com.snowcattle.game.db.async;

import com.alibaba.fastjson.JSON;
import com.snowcattle.game.db.common.JsonSerializer;
import com.snowcattle.game.db.common.enums.DbOperationEnum;

/**
 * Created by jiangwenping on 17/4/6.
 * 异步实体封装箱, 里面包含了实体的快照
 *  所有需要异步存储的实体，都会包装在这里，然后传递到异步队列里面
 */
public class AsyncEntityWrapper implements JsonSerializer{

    /**
     * 包装时间
     */
    private long wrapperTime;

    private DbOperationEnum dbOperationEnum;


    public AsyncEntityWrapper() {

    }

    public AsyncEntityWrapper(DbOperationEnum dbOperationEnum) {
        this.dbOperationEnum = dbOperationEnum;
        this.wrapperTime = System.currentTimeMillis();
    }

    public long getWrapperTime() {
        return wrapperTime;
    }

    public void setWrapperTime(long wrapperTime) {
        this.wrapperTime = wrapperTime;
    }

    public DbOperationEnum getDbOperationEnum() {
        return dbOperationEnum;
    }

    public void setDbOperationEnum(DbOperationEnum dbOperationEnum) {
        this.dbOperationEnum = dbOperationEnum;
    }

    @Override
    public String serialize() {
        return JSON.toJSONString(this);
    }

    @Override
    public void deserialize(String pack) {
//        AsyncEntityWrapper<T> tempWrapper = JSON.parseObject(pack, this.getClass());
//        this.setEntity(tempWrapper.getEntity());
//        this.setWrapperTime(tempWrapper.getWrapperTime());
//        this.setDbOperationEnum(tempWrapper.getDbOperationEnum());
    }
}
