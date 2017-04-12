package com.snowcattle.game.db.service.async.transaction.entity;

import com.redis.transaction.entity.AbstractGameTransactionEntity;
import com.redis.transaction.enums.GameTransactionCommitResult;
import com.redis.transaction.enums.GameTransactionEntityCause;
import com.redis.transaction.exception.GameTransactionException;
import com.redis.transaction.service.IRGTRedisService;

/**
 * Created by jwp on 2017/4/12.
 */
public class AsyncDBSaveTransactionEntity extends AbstractGameTransactionEntity {

    private IRGTRedisService redisService;

    public AsyncDBSaveTransactionEntity(GameTransactionEntityCause cause, String key, IRGTRedisService redisService) {
        super(cause, key, redisService);
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
