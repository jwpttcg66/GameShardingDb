package com.snowcattle.game.db.sharding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/3/28.
 */
public class EntityServiceShardingStrategy {

    private int dbCount;
    private int tableCount;
    private String dataSource;


    public String getShardingDBKeyByUserId(long userId) {
        long dbIndex = userId % dbCount;
        return dataSource+ dbIndex;
    }

    public int getShardingDBTableIndexByUserId(long userId){
        return (int) (userId%tableCount);
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
