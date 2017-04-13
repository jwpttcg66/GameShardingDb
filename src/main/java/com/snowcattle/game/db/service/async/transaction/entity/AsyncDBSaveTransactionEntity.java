package com.snowcattle.game.db.service.async.transaction.entity;

import com.redis.transaction.entity.AbstractGameTransactionEntity;
import com.redis.transaction.enums.GameTransactionCommitResult;
import com.redis.transaction.enums.GameTransactionEntityCause;
import com.redis.transaction.exception.GameTransactionException;
import com.redis.transaction.service.IRGTRedisService;
import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.service.async.AsyncEntityWrapper;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.service.redis.RedisService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

/**
 * Created by jwp on 2017/4/12.
 * 异步存储事务实体
 */
public class AsyncDBSaveTransactionEntity extends AbstractGameTransactionEntity {

    private Logger logger = Loggers.dbErrorLogger;
    /**
     * db中redis服务
     */
    private RedisService redisService;

    /**
     * 实体存储服务
     */
    private EntityService entityService;

    /**
     * 需要弹出的玩家key
     */
    private String playerKey;

    public AsyncDBSaveTransactionEntity(GameTransactionEntityCause cause, String playerKey, IRGTRedisService irgtRedisService, EntityService entityService, RedisService redisService) {
        super(cause, playerKey, irgtRedisService);
        this.playerKey = playerKey;
        this.entityService = entityService;
        this.redisService = redisService;
    }

    @Override
    public void commit() throws GameTransactionException {
        boolean startFlag = true;
        do {
            try {
                String popKey = redisService.lpop(playerKey);
                if(StringUtils.isEmpty(popKey)){
                    break;
                }

                //开始保存数据库
                AsyncEntityWrapper asyncEntityWrapper = new AsyncEntityWrapper();
                asyncEntityWrapper.deserialize(popKey);
                saveAsyncEntityWrapper(asyncEntityWrapper);
            }catch (Exception e){
                logger.error(e.toString(), e);
                startFlag = false;
            }


        }while(startFlag);
    }

    private void saveAsyncEntityWrapper(AsyncEntityWrapper asyncEntityWrapper) throws Exception {
        //开始进行反射，存储到mysql
        String className = asyncEntityWrapper.getSimpleClassName();
//        Class targeClasses = Class.forName(className);
//        AbstractEntity abstractEntity = (AbstractEntity) targeClasses.newInstance();
//        DbOperationEnum dbOperationEnum = asyncEntityWrapper.getDbOperationEnum();
//        if(dbOperationEnum.equals(DbOperationEnum.insert)){
//            entityService.insertEntity(abstractEntity);
//        }
    }
    @Override
    public void rollback() throws GameTransactionException {

    }

    @Override
    public GameTransactionCommitResult trycommit() throws GameTransactionException {
        return GameTransactionCommitResult.SUCCESS;
    }
}
