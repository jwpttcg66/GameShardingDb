package com.snowcattle.game.db.service.jdbc.test;

import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.impl.OrderService;
import com.snowcattle.game.db.service.proxy.EnityProxyService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class JdbcTest {
    public static void main(String[] args) throws  Exception{
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});
        insertTest(classPathXmlApplicationContext);
//        Order order = getTest(classPathXmlApplicationContext);
//        updateTest(classPathXmlApplicationContext, order);
//        deleteTest(classPathXmlApplicationContext, order);
          getListTest(classPathXmlApplicationContext);
    }

    public static void insertTest( ClassPathXmlApplicationContext classPathXmlApplicationContext){

        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        int startSize = 101;
        int endSize = 102;
        for(int i = startSize; i < endSize; i++){
            Order order = new Order();
            order.setUserId(i);
            order.setId((long) i);
            order.setStatus("测试插入" + i);
            orderService.insertOrder(order);
        }
    }

    public static Order getTest( ClassPathXmlApplicationContext classPathXmlApplicationContext){
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        long userId = 2;
        long id = 2;
        Order order = orderService.getOrder(userId, id);
        System.out.println(order);
        return order;
    }

    public static void getListTest( ClassPathXmlApplicationContext classPathXmlApplicationContext){
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        long userId = 2;
        List<Order> orderList = orderService.getOrderList(userId);
        System.out.println(orderList);
    }


    public static void updateTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, Order order) throws Exception {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        EnityProxyService enityProxyService = (EnityProxyService) classPathXmlApplicationContext.getBean("dbProxyService");
        Order proxyOrder = enityProxyService.createProxyEntity(order);
        proxyOrder.setStatus("修改了3");
        orderService.updateOrder(proxyOrder);

        long userId = 2;
        long id = 2;
        Order queryOrder = orderService.getOrder(userId, id);
        System.out.println(queryOrder.getStatus());
    }

    public static void deleteTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, Order order) throws Exception {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        orderService.deleteOrder(order);
        long userId = 2;
        long id = 2;
        Order queryOrder = orderService.getOrder(userId, id);
        System.out.println(queryOrder);
    }
}
