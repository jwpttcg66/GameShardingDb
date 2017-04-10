package com.snowcattle.game.db.service.jdbc.entity;

import com.snowcattle.game.db.cache.redis.RedisListInterface;
import com.snowcattle.game.db.common.annotation.DbMapper;
import com.snowcattle.game.db.common.annotation.EntitySave;
import com.snowcattle.game.db.common.annotation.FieldSave;
import com.snowcattle.game.db.common.annotation.MethodSaveProxy;
import com.snowcattle.game.db.entity.BaseLongIDEntity;
import com.snowcattle.game.db.service.jdbc.mapper.OrderMapper;

/**
 * Created by jwp on 2017/3/24.
 */
@EntitySave
@DbMapper(mapper = OrderMapper.class)
public class MoreOrder  extends BaseLongIDEntity implements RedisListInterface{


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
    public String getShardingKey() {
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
