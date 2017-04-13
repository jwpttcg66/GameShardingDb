package com.snowcattle.game.db.service.async.transaction.entity;

import com.redis.transaction.entity.AbstractGameTransactionEntity;
import com.redis.transaction.enums.GameTransactionCommitResult;
import com.redis.transaction.enums.GameTransactionEntityCause;
import com.redis.transaction.exception.GameTransactionException;
import com.redis.transaction.service.IRGTRedisService;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.service.redis.RedisService;

/**
 * Created by jwp on 2017/4/12.
 * 异步存储事务实体
 */
public class AsyncDBSaveTransactionEntity extends AbstractGameTransactionEntity {

    /**
     * db中redis服务
     */
    private RedisService redisService;

    /**
     * 实体存储服务
     */
    private EntityService entityService;
    public AsyncDBSaveTransactionEntity(GameTransactionEntityCause cause, String playerKey, IRGTRedisService irgtRedisService, EntityService entityService, RedisService redisService) {
        super(cause, playerKey, irgtRedisService);
        this.entityService = entityService;
        this.redisService = redisService;
    }

    @Override
    public void commit() throws GameTransactionException {

//        String testRedisKey =  "testRedis";
//        redisService.setString(testRedisKey, "1000");
    }

    @Override
    public void rollback() throws GameTransactionException {

    }

    @Override
    public GameTransactionCommitResult trycommit() throws GameTransactionException {
        return GameTransactionCommitResult.SUCCESS;
    }
}
