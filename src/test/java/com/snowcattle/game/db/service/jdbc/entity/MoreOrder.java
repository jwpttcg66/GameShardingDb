package com.snowcattle.game.db.service.jdbc.entity;

import com.snowcattle.game.db.cache.redis.RedisListInterface;
import com.snowcattle.game.db.util.EntityUtils;

/**
 * Created by jwp on 2017/3/24.
 */
public class MoreOrder extends Order implements RedisListInterface{

    @Override
    public String getUniqueKey() {
        return String.valueOf(getUserId());
    }

    @Override
    public String getSubUniqueKey() {
        return String.valueOf(getId());
    }

    @Override
    public String getRedisKeyEnumString() {
        return "mod#";
    }
}
