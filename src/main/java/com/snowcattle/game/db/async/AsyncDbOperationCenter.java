package com.snowcattle.game.db.async;

import com.snowcattle.game.db.cache.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/4/10.
 * 异步执行更新中心
 */
@Service
public class AsyncDbOperationCenter {

    @Autowired
    private RedisService redisService;

    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
