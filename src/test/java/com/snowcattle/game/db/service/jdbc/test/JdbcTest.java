package com.snowcattle.game.db.service.jdbc.test;

import com.snowcattle.game.db.service.jdbc.entity.MoreOrder;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.impl.OrderService;
import com.snowcattle.game.db.service.proxy.EnityProxyService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class JdbcTest {
    public static long userId = 99999;

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});
        OrderService orderService = getOrderService(classPathXmlApplicationContext);
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
       //test
        orderService.deleteEntityBatch(orderList);
    }

    public static void updateBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, List<Order> orderList) throws Exception {
        EnityProxyService enityProxyService = (EnityProxyService) classPathXmlApplicationContext.getBean("enityProxyService");
        List<Order> updateList = new ArrayList<>();
        for (Order order : orderList) {
            Order proxyOrder = enityProxyService.createProxyEntity(order);
            proxyOrder.setStatus("dddd");
            proxyOrder.setUserId(userId);
            proxyOrder.setId(order.getId());
            updateList.add(proxyOrder);
        }
        orderService.updateOrderList(updateList);
    }

    public static void insertBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
        int startSize = 200000;
        int endSize = startSize + 10;
        List<Order> list = new ArrayList<>();
        for (int i = startSize; i < endSize; i++) {
            MoreOrder order = new MoreOrder();
            order.setUserId(userId);
            order.setId(i);
            order.setStatus("测试列表插入" + i);
            list.add(order);
        }

        orderService.insertOrderList(list);
    }

    public static List<Order> getOrderList(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
        long userId = 99999;
        List<Order> order = orderService.getOrderList(userId);
        System.out.println(order);
        return order;
    }

    public static void insertTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {

        int startSize = 2600;
        int endSize = 2700;
        for (int i = startSize; i < endSize; i++) {
            Order order = new Order();
            order.setUserId(i);
            order.setId((long) i);
            order.setStatus("测试插入" + i);
            orderService.insertOrder(order);
        }
    }

    public static Order getTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {
        long id = 2;
        Order order = orderService.getOrder(userId, id);
        System.out.println(order);
        return order;
    }

    public static void getListTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {
        List<Order> orderList = orderService.getOrderList(userId);
        System.out.println(orderList);
    }


    public static void updateTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, Order order) throws Exception {
        EnityProxyService enityProxyService = (EnityProxyService) classPathXmlApplicationContext.getBean("enityProxyService");
        Order proxyOrder = enityProxyService.createProxyEntity(order);
        proxyOrder.setStatus("修改了3");
        orderService.updateOrder(proxyOrder);

        long id = 2;
        Order queryOrder = orderService.getOrder(userId, id);
        System.out.println(queryOrder.getStatus());
    }

    public static void deleteTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, Order order) throws Exception {
        orderService.deleteOrder(order);
        long id = 2;
        Order queryOrder = orderService.getOrder(userId, id);
        System.out.println(queryOrder);
    }

    public static OrderService getOrderService(ClassPathXmlApplicationContext classPathXmlApplicationContext) {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        return orderService;
    }
}
