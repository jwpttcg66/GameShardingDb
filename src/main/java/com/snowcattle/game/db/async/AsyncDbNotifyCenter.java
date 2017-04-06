package com.snowcattle.game.db.async;

import com.snowcattle.game.db.cache.redis.RedisService;
import com.snowcattle.game.db.entity.AbstractEntity;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.entity.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/4/6.
 * db异步注册数据通知中心
 */
@Service
public class AsyncDbNotifyCenter {

    @Autowired
    private RedisService redisService;

    public void asyncEnity(EntityService entityService, AsyncEntityWrapper asyncEntityWrapper){
        IEntity iEntity = asyncEntityWrapper.getEntity();
        //计算处于那个db
        AbstractEntity entity = (AbstractEntity) iEntity;
        long selectId = entityService.getShardingId(entity);
        int dbSelectId = entityService.getEntityServiceShardingStrategy().getShardingDBTableIndexByUserId(selectId);
//        redisService.lpushString()
    }
}
