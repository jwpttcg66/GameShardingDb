package com.snowcattle.game.db.service.jdbc.entity;

import com.snowcattle.game.db.cache.redis.AsyncCacheKey;
import com.snowcattle.game.db.cache.redis.RedisInterface;
import com.snowcattle.game.db.cache.redis.RedisKeyEnum;
import com.snowcattle.game.db.common.annotation.DbMapper;
import com.snowcattle.game.db.common.annotation.EntitySave;
import com.snowcattle.game.db.common.annotation.FieldSave;
import com.snowcattle.game.db.common.annotation.MethodSaveProxy;
import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.service.jdbc.mapper.OrderMapper;
import com.snowcattle.game.db.util.EntityUtils;

@EntitySave
@DbMapper(mapper = OrderMapper.class)
public class Order extends BaseEntity implements RedisInterface, AsyncCacheKey{

    @FieldSave
    private String status;

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    @MethodSaveProxy(proxy="status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + getId() +
                ", userId=" + getUserId() +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public String getUniqueKey() {
        return String.valueOf(getUserId()+ EntityUtils.ENTITY_SPLIT_STRING + getId());
    }

    @Override
    public String getRedisKeyEnumString() {
        return RedisKeyEnum.PLAYER.getKey();
    }

    @Override
    public String getAsyncCacheKey() {
        return RedisKeyEnum.ASYNC_PLAYER.getKey();
    }
}