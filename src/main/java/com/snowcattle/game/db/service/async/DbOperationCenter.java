package com.snowcattle.game.db.service.async;

import com.redis.transaction.service.RGTRedisService;
import com.redis.transaction.service.TransactionService;
import com.snowcattle.game.db.service.async.transaction.factory.DbGameTransactionEntityCauseFactory;
import com.snowcattle.game.db.service.async.transaction.factory.DbGameTransactionEntityFactory;
import com.snowcattle.game.db.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/4/12.
 */
@Service
public class DbOperationCenter {

    @Autowired
    private RedisService redisService;
    /**
     * 事务redis服务
     */
    @Autowired
    private RGTRedisService rgtRedisService;

    /**
     * 事务服务
     */
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DbGameTransactionEntityFactory dbGameTransactionEntityFactory;

    @Autowired
    private DbGameTransactionEntityCauseFactory dbGameTransactionEntityCauseFactory;

    public RGTRedisService getRgtRedisService() {
        return rgtRedisService;
    }

    public void setRgtRedisService(RGTRedisService rgtRedisService) {
        this.rgtRedisService = rgtRedisService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public DbGameTransactionEntityFactory getDbGameTransactionEntityFactory() {
        return dbGameTransactionEntityFactory;
    }

    public void setDbGameTransactionEntityFactory(DbGameTransactionEntityFactory dbGameTransactionEntityFactory) {
        this.dbGameTransactionEntityFactory = dbGameTransactionEntityFactory;
    }

    public DbGameTransactionEntityCauseFactory getDbGameTransactionEntityCauseFactory() {
        return dbGameTransactionEntityCauseFactory;
    }

    public void setDbGameTransactionEntityCauseFactory(DbGameTransactionEntityCauseFactory dbGameTransactionEntityCauseFactory) {
        this.dbGameTransactionEntityCauseFactory = dbGameTransactionEntityCauseFactory;
    }

    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
