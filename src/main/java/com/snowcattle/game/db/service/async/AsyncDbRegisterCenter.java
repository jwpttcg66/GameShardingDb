package com.snowcattle.game.db.service.async;

import com.snowcattle.game.db.common.enums.DbOperationEnum;
import com.snowcattle.game.db.entity.AbstractEntity;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.service.redis.AsyncRedisKeyEnum;
import com.snowcattle.game.db.service.redis.RedisInterface;
import com.snowcattle.game.db.service.redis.RedisListInterface;
import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by jiangwenping on 17/4/6.
 * db异步注册数据通知中心
 *  先把同类型的玩家数据都放在一个集合里，来保证对一个玩家的操作是顺讯执行的
 *
 *  然后把玩家变动通知，放入到玩家身上。
 */
@Service
public class AsyncDbRegisterCenter {

    @Autowired
    private RedisService redisService;

    public void asyncEntity(EntityService entityService, AsyncEntityWrapper asyncEntityWrapper, AbstractEntity entity){
        //计算处于那个db
        long selectId = entityService.getShardingId(entity);
        int dbSelectId = entityService.getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId);

        //加入到异步更新队列
        String unionKey = null;
        if(entity instanceof RedisInterface){
            unionKey = ((RedisInterface) entity).getUnionKey();
        }else if(entity instanceof RedisListInterface){
            unionKey = ((RedisListInterface) entity).getShardingKey();
        }

        //必须先push再sadd
        String simapleClassName = entity.getClass().getSimpleName();
        String aysncUnionKey = simapleClassName + "#" + unionKey;
        redisService.rPushString(aysncUnionKey, asyncEntityWrapper.serialize());
        redisService.saddString(AsyncRedisKeyEnum.ASYNC_DB.getKey() + dbSelectId + "#" + entity.getClass().getSimpleName(), aysncUnionKey);
    }

    public void asyncRegisterEntity(EntityService entityService, DbOperationEnum dbOperationEnum, AbstractEntity entity){
        AsyncEntityWrapper asyncEntityWrapper = null;
        if(dbOperationEnum.equals(DbOperationEnum.insert)){
            Map<String, String> map = EntityUtils.getCacheValueMap(entity);
            asyncEntityWrapper = new AsyncEntityWrapper(dbOperationEnum, entity.getClass().getSimpleName(), map);
        }else if(dbOperationEnum.equals(DbOperationEnum.update)){

        }else if(dbOperationEnum.equals(DbOperationEnum.delete)){

        }
        asyncEntity(entityService, asyncEntityWrapper, entity);
        System.out.println("异步保存");
    }
}
