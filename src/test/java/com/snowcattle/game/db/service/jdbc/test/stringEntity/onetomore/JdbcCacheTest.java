//package com.snowcattle.game.db.service.jdbc.test.stringEnity.onetomore;
//
//import com.snowcattle.game.db.service.jdbc.entity.MoreOrder;
//import com.snowcattle.game.db.service.jdbc.entity.Order;
//import com.snowcattle.game.db.service.jdbc.service.entity.impl.MoreOrderService;
//import com.snowcattle.game.db.service.jdbc.service.entity.impl.OrderService;
//import com.snowcattle.game.db.service.proxy.EntityProxyFactory;
//import com.snowcattle.game.db.service.proxy.EntityServiceProxyFactory;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by jiangwenping on 17/3/20.
// */
//public class JdbcCacheTest {
//    public static long userId = 99999;
//
//    public static void main(String[] args) throws Exception {
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
//        OrderService orderService = getOrderProxyService(classPathXmlApplicationContext);
////        insertTest(classPathXmlApplicationContext, orderService);
//        insertBatchTest(classPathXmlApplicationContext, orderService);
////        Order order = getTest(classPathXmlApplicationContext, orderService);
////        List<Order> orderList = getOrderList(classPathXmlApplicationContext, orderService);
////        updateTest(classPathXmlApplicationContext, orderService, order);
////        updateBatchTest(classPathXmlApplicationContext, orderService, orderList);
////        deleteTest(classPathXmlApplicationContext, orderService, order);
////        deleteBatchTest(classPathXmlApplicationContext, orderService, orderList);
//        getBatchOrderList(classPathXmlApplicationContext, orderService);
//
//    }
//
//    public static void deleteBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, List<Order> orderList) throws Exception {
//        com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore.JdbcTest.deleteBatchTest(classPathXmlApplicationContext, orderService, orderList);
//    }
//
//    public static List<MoreOrder> getBatchOrderList(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
//        EntityProxyFactory entityProxyFactory = (EntityProxyFactory) classPathXmlApplicationContext.getBean("entityProxyFactory");
//        MoreOrder moreOrder = new MoreOrder();
//        MoreOrder proxyOrder = entityProxyFactory.createProxyEntity(moreOrder);
////        MoreOrder proxyOrder = moreOrder;
//        proxyOrder.setUserId(userId);
//        proxyOrder.setStatus("测试列表插入" + com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore.JdbcTest.batchStart);
//        MoreOrderService moreOrderService = getMoreOrderProxyService(classPathXmlApplicationContext);
//        List<MoreOrder> orderList = moreOrderService.getEntityList(proxyOrder);
//        System.out.println(orderList);
//        return orderList;
//    }
//
//    public static void updateBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, List<Order> orderList) throws Exception {
//        com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore.JdbcTest.updateBatchTest(classPathXmlApplicationContext, orderService, orderList);
//    }
//
//    public static void insertBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
//        MoreOrderService moreOrderService = getMoreOrderProxyService(classPathXmlApplicationContext);
//        int startSize = com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore.JdbcTest.batchStart;
//        int endSize = startSize + 10;
//        List<MoreOrder> list = new ArrayList<>();
//        for (int i = startSize; i < endSize; i++) {
//            MoreOrder order = new MoreOrder();
//            order.setUserId(userId);
//            order.setId((long)i);
//            order.setStatus("测试列表插入" + i);
//            list.add(order);
//        }
//
//        moreOrderService.insertEntityBatch(list);
//    }
//
//    public static List<Order> getOrderList(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
//        return com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore.JdbcTest.getMoreOrderList(classPathXmlApplicationContext, orderService);
//    }
//
//    public static void insertTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {
//        com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore.JdbcTest.insertTest(classPathXmlApplicationContext, orderService);
//    }
//
//    public static Order getTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {
//       return com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore.JdbcTest.getTest(classPathXmlApplicationContext, orderService);
//    }
//
//    public static void updateTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, Order order) throws Exception {
//       com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore.JdbcTest.updateTest(classPathXmlApplicationContext, orderService, order);
//    }
//
//    public static void deleteTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, Order order) throws Exception {
//      com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore.JdbcTest.deleteTest(classPathXmlApplicationContext, orderService, order);
//    }
//
//    public static OrderService getOrderProxyService(ClassPathXmlApplicationContext classPathXmlApplicationContext) throws Exception {
//        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
//        EntityServiceProxyFactory entityServiceProxyFactory = (EntityServiceProxyFactory) classPathXmlApplicationContext.getBean("entityServiceProxyFactory");
//        orderService = entityServiceProxyFactory.createProxyService(orderService);
//        return orderService;
//    }
//
//    public static MoreOrderService getMoreOrderProxyService(ClassPathXmlApplicationContext classPathXmlApplicationContext) throws Exception {
//        MoreOrderService moreOrderService = (MoreOrderService) classPathXmlApplicationContext.getBean("moreOrderService");
//        EntityServiceProxyFactory entityServiceProxyFactory = (EntityServiceProxyFactory) classPathXmlApplicationContext.getBean("entityServiceProxyFactory");
//        moreOrderService = entityServiceProxyFactory.createProxyService(moreOrderService);
//        return moreOrderService;
//    }
//}
