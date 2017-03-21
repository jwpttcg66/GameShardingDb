package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;

/**
 * Created by jiangwenping on 17/3/21.
 */
public class EntityService<T extends IEntity>{

    public int insertEntity(IDBMapper<T> idbMapper, T entity){
        return idbMapper.insertEntity(entity);
    }
}
