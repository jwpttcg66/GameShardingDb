package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;
import com.snowcattle.game.db.sharding.CustomerContextHolder;
import com.snowcattle.game.db.sharding.DataSourceType;

/**
 * Created by jiangwenping on 17/3/21.
 */
public class EntityService<T extends BaseEntity>{

    public int insertEntity(IDBMapper<T> idbMapper, T entity){
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, entity.getUserId()));
        entity.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(entity.getUserId()));
        return idbMapper.insertEntity(entity);
    }
}
