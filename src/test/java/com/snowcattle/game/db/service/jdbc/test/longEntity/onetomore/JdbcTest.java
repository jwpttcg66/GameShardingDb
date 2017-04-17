package com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore;

import com.snowcattle.game.db.service.jdbc.entity.MoreOrder;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.MoreOrderService;
import com.snowcattle.game.db.service.proxy.EntityProxyFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class JdbcTest {
    public static long userId = 99999;
    public static long id = 3603;
    public static int batchStart = 90011110;

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        MoreOrderService moreOrderService = getMoreOrderService(classPathXmlApplicationContext);
        insertTest(classPathXmlApplicationContext, moreOrderService);
        insertBatchTest(classPathXmlApplicationContext, moreOrderService);
        MoreOrder moreOrder = getTest(classPathXmlApplicationContext, moreOrderService);
        List<MoreOrder> orderList = getMoreOrderList(classPathXmlApplicationContext, moreOrderService);
        updateTest(classPathXmlApplicationContext, moreOrderService, moreOrder);
        updateBatchTest(classPathXmlApplicationContext, moreOrderService, orderList);
        deleteTest(classPathXmlApplicationContext, moreOrderService, moreOrder);
        deleteBatchTest(classPathXmlApplicationContext, moreOrderService, orderList);
        getMoreOrderList(classPathXmlApplicationContext, moreOrderService);
    }



    public static void deleteBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, MoreOrderService moreOrderService, List<MoreOrder> orderList) throws Exception {
       //test2
        moreOrderService.deleteEntityBatch(orderList);
    }

    public static void updateBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, MoreOrderService moreOrderService, List<MoreOrder> orderList) throws Exception {
        EntityProxyFactory entityProxyFactory = (EntityProxyFactory) classPathXmlApplicationContext.getBean("entityProxyFactory");
        List<MoreOrder> updateList = new ArrayList<>();
        for (MoreOrder moreOrder : orderList) {
            MoreOrder proxyOrder = entityProxyFactory.createProxyEntity(moreOrder);
            proxyOrder.setStatus("dddd");
            proxyOrder.setUserId(userId);
            proxyOrder.setId(moreOrder.getId());
            updateList.add(proxyOrder);
        }
        moreOrderService.updateMoreOrderList(updateList);
    }

    public static void insertBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, MoreOrderService moreOrderService) throws Exception {
        int startSize = batchStart;
        int endSize = startSize + 10;
        List<MoreOrder> list = new ArrayList<>();
        for (int i = startSize; i < endSize; i++) {
            MoreOrder moreOrder = new MoreOrder();
            moreOrder.setUserId(userId);
            moreOrder.setId((long)i);
            moreOrder.setStatus("测试列表插入" + i);
            list.add(moreOrder);
        }

        moreOrderService.insertMoreOrderList(list);
    }

    public static List<MoreOrder> getMoreOrderList(ClassPathXmlApplicationContext classPathXmlApplicationContext, MoreOrderService moreOrderService) throws Exception {
        List<MoreOrder> moreOrderList = moreOrderService.getMoreOrderList(userId);
        System.out.println(moreOrderList);
        return moreOrderList;
    }

    public static void insertTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, MoreOrderService moreOrderService) {

        int startSize = batchStart;
        int endSize = batchStart+1;

        for (int i = startSize; i < endSize; i++) {
            MoreOrder moreOrder = new MoreOrder();
            moreOrder.setUserId(userId);
            moreOrder.setId((long) i);
            moreOrder.setStatus("测试插入" + i);
            moreOrderService.insertMoreOrder(moreOrder);

        }
    }

    public static MoreOrder getTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, MoreOrderService moreOrderService) {
        MoreOrder moreOrder = moreOrderService.getMoreOrder(userId, id);
        System.out.println(moreOrder);
        return moreOrder;
    }


    public static void updateTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, MoreOrderService moreOrderService, MoreOrder moreOrder) throws Exception {
        EntityProxyFactory entityProxyFactory = (EntityProxyFactory) classPathXmlApplicationContext.getBean("entityProxyFactory");
        MoreOrder proxyOrder = entityProxyFactory.createProxyEntity(moreOrder);
        proxyOrder.setStatus("修改了3");
        moreOrderService.updateMoreOrder(proxyOrder);

        MoreOrder queryOrder = moreOrderService.getMoreOrder(userId, id);
        System.out.println(queryOrder.getStatus());
    }

    public static void deleteTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, MoreOrderService moreOrderService, MoreOrder moreOrder) throws Exception {
        moreOrderService.deleteMoreOrder(moreOrder);
        MoreOrder queryOrder = moreOrderService.getMoreOrder(userId, id);
        System.out.println(queryOrder);
    }

    public static MoreOrderService getMoreOrderService(ClassPathXmlApplicationContext classPathXmlApplicationContext) {
        MoreOrderService moreOrderService = (MoreOrderService) classPathXmlApplicationContext.getBean("moreOrderService");
        return moreOrderService;
    }
}
