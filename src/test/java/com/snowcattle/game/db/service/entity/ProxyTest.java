package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.service.proxy.DbProxyService;
import com.snowcattle.game.db.service.proxy.EntityProxy;
import junit.framework.Test;

import java.util.Date;

/**
 * Created by jwp on 2017/3/16.
 */
public class ProxyTest {
    public static void main(String[] args) {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(1L);
        testEntity.setDeleted(false);
        testEntity.setDeleteTime(new Date());
        DbProxyService dbProxyService = new DbProxyService();
        EntityProxy entityProxy = dbProxyService.createProxy(testEntity);
        TestEntity proxyTestEntity = (TestEntity) dbProxyService.getProxyEntity(entityProxy);
        proxyTestEntity.setId(2L);
        System.out.println(entityProxy.isDirtyFlag());

    }
}
