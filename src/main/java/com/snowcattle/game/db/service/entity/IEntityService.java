package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;

import java.util.List;

/**
 * Created by jwp on 2017/3/23.
 */
public interface IEntityService <T extends BaseEntity>{
    public int insertEntity(T entity);
    public IEntity getEntity(T entity);
    public List<T> getEntityList(T entity);
    public void updateEntity(T entity);
    public void deleteEntity(T entity);
}
