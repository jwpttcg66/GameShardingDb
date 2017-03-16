package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.entity.BaseEntity;

/**
 * Created by jwp on 2017/3/16.
 */
public class EntityProxyWrapper<T extends BaseEntity> {

    //代理后的对象
    private T proxyEntity;

    private EntityProxy entityProxy;

    public EntityProxyWrapper(T proxyEntity, EntityProxy entityProxy) {
        this.proxyEntity = proxyEntity;
        this.entityProxy = entityProxy;
    }

    public T getProxyEntity() {
        return proxyEntity;
    }

    public void setProxyEntity(T proxyEntity) {
        this.proxyEntity = proxyEntity;
    }

    public EntityProxy getEntityProxy() {
        return entityProxy;
    }

    public void setEntityProxy(EntityProxy entityProxy) {
        this.entityProxy = entityProxy;
    }
}
