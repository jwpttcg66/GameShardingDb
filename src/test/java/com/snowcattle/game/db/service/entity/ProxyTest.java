package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.service.proxy.DbProxyService;

import java.util.Date;

/**
 * Created by jwp on 2017/3/16.
 */
public class ProxyTest {
    public static void main(String[] args) throws  Exception{
        TestEntity testEntity = new TestEntity();
        testEntity.setId(1L);
        testEntity.setDeleted(false);
        testEntity.setDeleteTime(new Date());
        DbProxyService dbProxyService = new DbProxyService();
//        EntityProxyWrapper<TestEntity> entityEntityProxyWrapper = dbProxyService.createEntityProxyWrapper(testEntity);
//        TestEntity proxyEntity = entityEntityProxyWrapper.getProxyEntity();
//        proxyEntity.setId(2L);
//        System.out.println(entityEntityProxyWrapper.getEntityProxy().isDirtyFlag());
//        proxyEntity.setId(2L);
//        System.out.println(entityEntityProxyWrapper.getEntityProxy().isDirtyFlag());
        TestEntity proxyEntity = (TestEntity) dbProxyService.initProxyWrapper(testEntity);
        proxyEntity.setId(2L);
        System.out.println(proxyEntity.getEntityProxyWrapper().getEntityProxy().isDirtyFlag());
        proxyEntity.setId(2L);
        System.out.println(proxyEntity.getEntityProxyWrapper().getEntityProxy().isDirtyFlag());
    }
}
