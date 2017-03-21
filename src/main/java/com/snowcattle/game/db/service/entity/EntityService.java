package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;
import com.snowcattle.game.db.sharding.CustomerContextHolder;
import com.snowcattle.game.db.sharding.DataSourceType;

/**
 * Created by jiangwenping on 17/3/21.
 * 实体服务
 */
public class EntityService<T extends BaseEntity>{

    public int insertEntity(IDBMapper<T> idbMapper, T entity){
        long selectId = getSelectId(entity);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, selectId));
        entity.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(selectId));
        return idbMapper.insertEntity(entity);
    }

    //获取分库主键
    private long getSelectId(T entity){
        long selectId = entity.getUserId();
        if(entity.getEntityKeyShardingStrategyEnum().equals(EntityKeyShardingStrategyEnum.ID)){
            selectId = entity.getId();
        }
        return selectId;
    }
}
