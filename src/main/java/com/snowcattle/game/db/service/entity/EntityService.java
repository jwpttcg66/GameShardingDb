package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.common.annotation.*;
import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;
import com.snowcattle.game.db.service.proxy.EntityProxyWrapper;
import com.snowcattle.game.db.sharding.CustomerContextHolder;
import com.snowcattle.game.db.sharding.DataSourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangwenping on 17/3/21.
 * 模版实体数据提服务
 */
public abstract class EntityService<T extends BaseEntity> implements IEntityService<T>{

    /**
     * 插入实体
     * @param idbMapper
     * @param entity
     * @return
     */
    @Override
    @DbOperation(operation = "insert")
    public int insertEntity(IDBMapper<T> idbMapper, T entity){
        long selectId = getShardingId(entity);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        entity.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(selectId));
        return idbMapper.insertEntity(entity);
    }

    /**
     * 查询实体
     * @param idbMapper
     * @param id
     * @param userId
     * @param entityKeyShardingStrategyEnum
     * @return
     */
    @DbOperation(operation = "query")
    public IEntity getEntity(IDBMapper<T> idbMapper, EntityKeyShardingStrategyEnum entityKeyShardingStrategyEnum, long userId, long id){
        long selectId = getShardingId(id, userId, entityKeyShardingStrategyEnum);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        int sharding_table_index = CustomerContextHolder.getShardingDBTableIndexByUserId(selectId);
        Map hashMap = new HashMap<>();
        hashMap.put("sharding_table_index", sharding_table_index);
        hashMap.put("id", id);
        hashMap.put("userId", userId);
        return idbMapper.getEntity(hashMap);
    }

    @DbOperation(operation = "queryList")
    public List<T> getEntityList(IDBMapper<T> idbMapper, EntityKeyShardingStrategyEnum entityKeyShardingStrategyEnum, long userId){
        long selectId = userId;
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        int sharding_table_index = CustomerContextHolder.getShardingDBTableIndexByUserId(selectId);
        Map hashMap = new HashMap<>();
        hashMap.put("sharding_table_index", sharding_table_index);
        hashMap.put("userId", userId);
        return idbMapper.getEntityList(hashMap);
    }

    /**
     * 修改实体
     * @param idbMapper
     * @param entity
     */
    @DbOperation(operation = "upadte")
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

    /**
     * 删除实体
     * @param idbMapper
     * @param entity
     */
    @DbOperation(operation = "delete")
    public void deleteEntity(IDBMapper<T> idbMapper, T entity){
        long selectId = getShardingId(entity);;
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        entity.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(selectId));
        idbMapper.deleteEntity(entity);
    }

    //获取分库主键
    private long getShardingId(T entity){
        long shardingId = entity.getUserId();
        if(entity.getEntityKeyShardingStrategyEnum().equals(EntityKeyShardingStrategyEnum.ID)){
            shardingId = entity.getId();
        }
        return shardingId;
    }

    //获取分库主键
    private long getShardingId(long id, long userId, EntityKeyShardingStrategyEnum entityKeyShardingStrategyEnum){
        long shardingId = userId;
        if(entityKeyShardingStrategyEnum.equals(EntityKeyShardingStrategyEnum.ID)){
            shardingId = id;
        }
        return shardingId;
    }
}
