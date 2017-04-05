package com.snowcattle.game.db.service.entity;

import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.proxy.EnityProxyService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jwp on 2017/3/16.
 */
public class ProxyTest {
    public static void main(String[] args) throws  Exception{
        TestEntity testEntity = new TestEntity();
        testEntity.setId(1L);
        testEntity.setDeleted(false);
        testEntity.setDeleteTime(new Date());
        EnityProxyService enityProxyService = new EnityProxyService();
//        EntityProxyWrapper<TestEntity> entityEntityProxyWrapper = dbProxyService.createEntityProxyWrapper(testEntity);
//        TestEntity proxyEntity = entityEntityProxyWrapper.getProxyEntity();
//        proxyEntity.setId(2L);
//        System.out.println(entityEntityProxyWrapper.getEntityProxy().isDirtyFlag());
//        proxyEntity.setId(2L);
//        System.out.println(entityEntityProxyWrapper.getEntityProxy().isDirtyFlag());
        TestEntity proxyEntity = enityProxyService.createProxyEntity(testEntity);
        proxyEntity.setId(2L);
        System.out.println(proxyEntity.getEntityProxyWrapper().getEntityProxy().isDirtyFlag());
        proxyEntity.setId(2L);
        System.out.println(proxyEntity.getEntityProxyWrapper().getEntityProxy().isDirtyFlag());

    }

    public void testListTransfer(){
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        order.setId(1L);
        orderList.add(order);
        List<IEntity> list = ( List<IEntity>)(List)orderList;
        System.out.println(list);
    }
}
