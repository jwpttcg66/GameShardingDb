package com.snowcattle.game.db.service.async.thread;

import com.snowcattle.game.db.entity.AbstractEntity;
import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.thread.worker.AbstractWork;

import java.util.TimerTask;

/**
 * Created by jiangwenping on 17/4/10.
 * 异步执行 玩家数据
 */
public class AsyncSaveWorker<T extends EntityService> extends AbstractWork {

    private EntityService entityService;

    private RedisService redisService;

    private String  playerKey;

    public AsyncSaveWorker(EntityService entityService, RedisService redisService, String playerKey) {
        this.entityService = entityService;
        this.redisService = redisService;
        this.playerKey = playerKey;
    }

    @Override
    public void run() {

    }

}
