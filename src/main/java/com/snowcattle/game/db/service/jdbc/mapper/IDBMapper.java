package com.snowcattle.game.db.service.jdbc.mapper;

import com.snowcattle.game.db.entity.IEntity;

/**
 * Created by jiangwenping on 17/3/21.
 */
public interface IDBMapper<T extends IEntity> {
    public int insertEntity(T entity);
}
