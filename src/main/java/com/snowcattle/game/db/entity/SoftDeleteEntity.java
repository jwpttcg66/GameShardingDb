package com.snowcattle.game.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jiangwenping on 17/3/16.
 * 所有对象都是先设置删除时间，删除标志，设置缓存，然后同步db异步操作执行删除后，执行回调，然后删除缓存
 * 缓存获取对象的时候，需要过滤已经删除的对象
 */
public interface SoftDeleteEntity<ID extends Serializable> extends IEntity<ID> {
    /**
     *
     * @return
     */
    public int getDeleted();

    /**
     * 取得实体被删除的时间
     *
     * @return
     */
    public Timestamp getDeleteTime();

    public void setDeleted(int deleted);
    public void setDeleteTime(Timestamp deleteTime);
}
