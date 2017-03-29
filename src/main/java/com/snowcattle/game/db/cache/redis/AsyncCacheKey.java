package com.snowcattle.game.db.cache.redis;

/**
 * Created by jiangwenping on 17/3/29.
 * 异步存储的时候异步key
 */
public interface AsyncCacheKey {
    public String getAsyncCacheKey();
}
