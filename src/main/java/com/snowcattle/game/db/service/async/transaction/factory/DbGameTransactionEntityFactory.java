package com.snowcattle.game.db.service.async.transaction.factory;

import com.redis.transaction.enums.GameTransactionEntityCause;
import com.redis.transaction.factory.GameTransactionEntityFactory;
import com.redis.transaction.service.IRGTRedisService;
import com.snowcattle.game.db.service.async.thread.AsyncDbOperation;
import com.snowcattle.game.db.service.async.transaction.entity.AsyncDBSaveTransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/4/12.
 */
@Service
public class DbGameTransactionEntityFactory extends GameTransactionEntityFactory {

    @Autowired
    private DbGameTransactionKeyFactory dbGameTransactionKeyFactory;

    public  AsyncDBSaveTransactionEntity createAsyncDBSaveTransactionEntity(GameTransactionEntityCause cause,IRGTRedisService redisService, String redisKey, String union){
        String key = dbGameTransactionKeyFactory.getPlayerTransactionEntityKey(cause, redisKey, union);
        AsyncDBSaveTransactionEntity asyncDBSaveTransactionEntity = new AsyncDBSaveTransactionEntity(cause, union, redisService);
        return asyncDBSaveTransactionEntity;
    }

    public DbGameTransactionKeyFactory getDbGameTransactionKeyFactory() {
        return dbGameTransactionKeyFactory;
    }

    public void setDbGameTransactionKeyFactory(DbGameTransactionKeyFactory dbGameTransactionKeyFactory) {
        this.dbGameTransactionKeyFactory = dbGameTransactionKeyFactory;
    }
}
