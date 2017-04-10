package com.snowcattle.game.db.service.async.thread;

import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.service.entity.EntityService;

import java.util.TimerTask;

/**
 * Created by jiangwenping on 17/4/10.
 */
public class AsyncDbSelectTimeWorker <T extends EntityService> extends TimerTask {

    private EntityService entityService;

    private RedisService redisService;

    public AsyncDbSelectTimeWorker(EntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    public void run() {

    }
}
