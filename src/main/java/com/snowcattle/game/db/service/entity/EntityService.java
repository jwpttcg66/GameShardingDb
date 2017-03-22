package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;
import com.snowcattle.game.db.service.proxy.EntityProxyWrapper;
import com.snowcattle.game.db.sharding.CustomerContextHolder;
import com.snowcattle.game.db.sharding.DataSourceType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangwenping on 17/3/21.
 * 实体服务
 */
public class EntityService<T extends BaseEntity>{

    public int insertEntity(IDBMapper<T> idbMapper, T entity){
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        entity.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(selectId));
        return idbMapper.insertEntity(entity);
    }


    public IEntity getEntity(IDBMapper<T> idbMapper, long id, long userId, EntityKeyShardingStrategyEnum entityKeyShardingStrategyEnum){
        long selectId = getShardingId(id, userId, entityKeyShardingStrategyEnum);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        int sharding_table_index = CustomerContextHolder.getShardingDBTableIndexByUserId(selectId);
        Map hashMap = new HashMap<>();
        hashMap.put("sharding_table_index", sharding_table_index);
        hashMap.put("id", id);
        hashMap.put("userId", userId);
        return idbMapper.getEntity(hashMap);
    }


    public void updateEntity(IDBMapper<T> idbMapper, T entity) {
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        int sharding_table_index = CustomerContextHolder.getShardingDBTableIndexByUserId(selectId);
        Map hashMap = new HashMap<>();
        hashMap.put("sharding_table_index", sharding_table_index);
        hashMap.put("userId", entity.getUserId());
        hashMap.put("id", entity.getId());
        EntityProxyWrapper entityProxyWrapper = entity.getEntityProxyWrapper();
        if(entityProxyWrapper != null){
            hashMap.putAll(entityProxyWrapper.getEntityProxy().getChangeParamSet());
        }
        idbMapper.updateEntityByMap(hashMap);
    }

    //获取分库主键
    private long getShardingId(T entity){
        long shardingId = entity.getUserId();
        if(entity.getEntityKeyShardingStrategyEnum().equals(EntityKeyShardingStrategyEnum.ID)){
            shardingId = entity.getId();
        }
        return shardingId;
    }

    private long getShardingId(long id, long userId, EntityKeyShardingStrategyEnum entityKeyShardingStrategyEnum){
        long shardingId = userId;
        if(entityKeyShardingStrategyEnum.equals(EntityKeyShardingStrategyEnum.ID)){
            shardingId = id;
        }
        return shardingId;
    }

}
