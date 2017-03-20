package com.snowcattle.game.db.service.jdbc.test;

import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.impl.OrderService;
import com.snowcattle.game.db.service.proxy.DbProxyService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class JdbcTest {
    public static void main(String[] args) throws  Exception{
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});
//        insertTest(classPathXmlApplicationContext);
        Order order = getTest(classPathXmlApplicationContext);
        updateTest(classPathXmlApplicationContext, order);
    }

    public static void insertTest( ClassPathXmlApplicationContext classPathXmlApplicationContext){

        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        for(int i = 0; i < 12; i++){
            Order order = new Order();
            order.setUserId(i);
            order.setOrderId(i);
            order.setStatus("测试插入" + i);
            orderService.insertOrder(order);
        }
    }

    public static Order getTest( ClassPathXmlApplicationContext classPathXmlApplicationContext){
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        long userId = 2;
        long orderId = 2;
        Order order = orderService.getOrder(userId, orderId);
        System.out.println(order);
        return order;
    }

    public static void updateTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, Order order) throws Exception {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        DbProxyService dbProxyService = new DbProxyService();
        Order proxyOrder = dbProxyService.initProxyWrapper(Order.class, order);
        proxyOrder.setStatus("修改了");
        orderService.updateOrder(proxyOrder);

        long userId = 2;
        long orderId = 2;
        Order queryOrder = orderService.getOrder(userId, orderId);
        System.out.println(queryOrder);
    }
}
