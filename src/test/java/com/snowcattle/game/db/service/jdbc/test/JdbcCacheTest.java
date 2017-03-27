package com.snowcattle.game.db.service.jdbc.test;

import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.impl.OrderService;
import com.snowcattle.game.db.service.proxy.EntityServiceProxyFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class JdbcCacheTest {
    public static long userId = 99999;

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});
        OrderService orderService = getOrderProxyService(classPathXmlApplicationContext);
//        insertTest(classPathXmlApplicationContext, orderService);
//        insertBatchTest(classPathXmlApplicationContext, orderService);
//        Order order = getTest(classPathXmlApplicationContext, orderService);
//        List<Order> orderList = getOrderList(classPathXmlApplicationContext, orderService);
//        updateTest(classPathXmlApplicationContext, orderService, order);
//        updateBatchTest(classPathXmlApplicationContext, orderService, orderList);
//        deleteTest(classPathXmlApplicationContext, orderService, order);
//        deleteBatchTest(classPathXmlApplicationContext, orderService, orderList);
//        getListTest(classPathXmlApplicationContext, orderService);
    }


    public static void deleteBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, List<Order> orderList) throws Exception {
        JdbcTest.deleteBatchTest(classPathXmlApplicationContext, orderService, orderList);
    }

    public static void updateBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, List<Order> orderList) throws Exception {
       JdbcTest.updateBatchTest(classPathXmlApplicationContext, orderService, orderList);
    }

    public static void insertBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
      JdbcTest.insertBatchTest(classPathXmlApplicationContext, orderService);
    }

    public static List<Order> getOrderList(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
      return JdbcTest.getOrderList(classPathXmlApplicationContext, orderService);
    }

    public static void insertTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {
        JdbcTest.insertTest(classPathXmlApplicationContext, orderService);
    }

    public static Order getTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {
       return JdbcTest.getTest(classPathXmlApplicationContext, orderService);
    }

    public static void getListTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {
        JdbcTest.getListTest(classPathXmlApplicationContext, orderService);
    }


    public static void updateTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, Order order) throws Exception {
       JdbcTest.updateTest(classPathXmlApplicationContext, orderService, order);
    }

    public static void deleteTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, Order order) throws Exception {
      JdbcTest.deleteTest(classPathXmlApplicationContext, orderService, order);
    }

    public static OrderService getOrderProxyService(ClassPathXmlApplicationContext classPathXmlApplicationContext) throws Exception {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        EntityServiceProxyFactory entityServiceProxyFactory = (EntityServiceProxyFactory) classPathXmlApplicationContext.getBean("entityServiceProxyFactory");
        orderService = entityServiceProxyFactory.createProxyService(orderService);
        return orderService;
    }
}
