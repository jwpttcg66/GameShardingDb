package com.snowcattle.game.db.service.jdbc.test;

import com.snowcattle.game.db.service.jdbc.entity.MoreOrder;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.impl.OrderService;
import com.snowcattle.game.db.service.proxy.EnityProxyService;
import com.snowcattle.game.db.service.proxy.EntityServiceProxyService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by jwp on 2017/3/23.
 */
public class JdbcCacheTest {

    public static final long oldId = 208;
    public static final long id = 208;
    public static final long userId = id;
    public static void main(String[] args) throws  Exception{
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});
//        insertTest(classPathXmlApplicationContext);
        insertListTest(classPathXmlApplicationContext);
        Order order = getTest(classPathXmlApplicationContext);
        updateTest(classPathXmlApplicationContext, order);
//        deleteTest(classPathXmlApplicationContext, order);
//        getListTest(classPathXmlApplicationContext);
    }

    public static void insertTest( ClassPathXmlApplicationContext classPathXmlApplicationContext) throws  Exception{

        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        EntityServiceProxyService entityServiceProxyService = (EntityServiceProxyService) classPathXmlApplicationContext.getBean("entityServiceProxyService");
        orderService = entityServiceProxyService.createProxyService(orderService);
        Order order = new Order();
        order.setUserId(userId);
        order.setId(id);
        order.setStatus("测试插入" + id);
        orderService.insertOrder(order);
    }

    public static void insertListTest( ClassPathXmlApplicationContext classPathXmlApplicationContext) throws  Exception{

        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        EntityServiceProxyService entityServiceProxyService = (EntityServiceProxyService) classPathXmlApplicationContext.getBean("entityServiceProxyService");

        orderService = entityServiceProxyService.createProxyService(orderService);

        int startSize = 20000;
        int endSize = startSize + 10;
        for(int i = startSize; i < endSize; i++) {
            MoreOrder order = new MoreOrder();
            order.setUserId(userId);
            order.setId(id);
            order.setStatus("测试列表插入" + id);
            orderService.insertOrder(order);
        }
    }

    public static Order getTest( ClassPathXmlApplicationContext classPathXmlApplicationContext) throws Exception {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        EntityServiceProxyService entityServiceProxyService = (EntityServiceProxyService) classPathXmlApplicationContext.getBean("entityServiceProxyService");
        orderService = entityServiceProxyService.createProxyService(orderService);
        Order order = orderService.getOrder(userId, id);
        System.out.println(order);
        return order;
    }

    public static void getListTest( ClassPathXmlApplicationContext classPathXmlApplicationContext){
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        List<Order> orderList = orderService.getOrderList(userId);
        System.out.println(orderList);
    }


    public static void updateTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, Order order) throws Exception {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        EntityServiceProxyService entityServiceProxyService = (EntityServiceProxyService) classPathXmlApplicationContext.getBean("entityServiceProxyService");
        orderService = entityServiceProxyService.createProxyService(orderService);
        EnityProxyService enityProxyService = (EnityProxyService) classPathXmlApplicationContext.getBean("enityProxyService");
        Order proxyOrder = enityProxyService.createProxyEntity(order);
        proxyOrder.setStatus("修改了3");
        orderService.updateOrder(proxyOrder);

        Order queryOrder = orderService.getOrder(userId, id);
        System.out.println(queryOrder);
    }

    public static void deleteTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, Order order) throws Exception {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        orderService.deleteOrder(order);
        Order queryOrder = orderService.getOrder(userId, id);
        System.out.println(queryOrder);
    }
}
