package com.snowcattle.game.db.service.async;

import com.alibaba.fastjson.JSON;
import com.snowcattle.game.db.common.JsonSerializer;
import com.snowcattle.game.db.common.enums.DbOperationEnum;

import java.util.HashMap;
import java.util.Map;

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

    /**
     *
     */
    private DbOperationEnum dbOperationEnum;

    /**
     *  名字的简写，用于反射生成对象
     */
    private String simpleClassName;

    private Map<String ,String> prarms = new HashMap<>();

    public AsyncEntityWrapper() {

    }

    public AsyncEntityWrapper(DbOperationEnum dbOperationEnum, String simpleClassName, Map<String ,String> prarms)  {
        this.dbOperationEnum = dbOperationEnum;
        this.wrapperTime = System.currentTimeMillis();
        this.simpleClassName = simpleClassName;
        this.prarms = prarms;
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
        AsyncEntityWrapper asyncEntityWrapper= JSON.parseObject(pack, this.getClass());
        this.setWrapperTime(asyncEntityWrapper.getWrapperTime());
        this.setDbOperationEnum(asyncEntityWrapper.getDbOperationEnum());
        this.setPrarms(asyncEntityWrapper.getPrarms());
    }

    public AsyncEntityWrapper(long wrapperTime, DbOperationEnum dbOperationEnum, Map<String, String> prarms) {
        this.wrapperTime = wrapperTime;
        this.dbOperationEnum = dbOperationEnum;
        this.prarms = prarms;
    }

    public Map<String, String> getPrarms() {
        return prarms;
    }

    public void setPrarms(Map<String, String> prarms) {
        this.prarms = prarms;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }
}
