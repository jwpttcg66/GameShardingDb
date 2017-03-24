package com.snowcattle.game.db.service.jdbc.mapper;

import com.snowcattle.game.db.entity.IEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangwenping on 17/3/21.
 */
public interface IDBMapper<T extends IEntity> {
    public int insertEntity(T entity);
    public IEntity getEntity(T entity);
    public List<T> getEntityList(T entity);
    public void updateEntityByMap(Map map);
    public void deleteEntity(T entity);
}
