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

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }
}
