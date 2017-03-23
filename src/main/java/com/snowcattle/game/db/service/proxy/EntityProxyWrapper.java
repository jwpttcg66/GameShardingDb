package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.entity.BaseEntity;

/**
 * Created by jwp on 2017/3/16.
 * 代理封装
 */
public class EntityProxyWrapper<T extends BaseEntity> {

    private EntityProxy entityProxy;

    public EntityProxyWrapper(EntityProxy entityProxy) {
        this.entityProxy = entityProxy;
    }

    public EntityProxy getEntityProxy() {
        return entityProxy;
    }

    public void setEntityProxy(EntityProxy entityProxy) {
        this.entityProxy = entityProxy;
    }
}
