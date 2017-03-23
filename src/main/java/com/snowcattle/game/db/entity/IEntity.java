package com.snowcattle.game.db.entity;

import java.io.Serializable;

/**
 * Created by jiangwenping on 17/3/16.
 * 基本的数据存储对象
 */
public interface IEntity extends Serializable {
    public void setId(long id);
    public long getId();
}

