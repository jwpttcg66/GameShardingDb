package com.snowcattle.game.db.service.jdbc.test;

import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.impl.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class JdbcTest {
    public static void main(String[] args) throws  Exception{
//        insertTest();
        getTest();
    }

    public static void insertTest(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});

        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        for(int i = 0; i < 12; i++){
            Order order = new Order();
            order.setUserId(i);
            order.setOrderId(i);
            order.setStatus("测试插入" + i);
            orderService.insertOrder(order);
        }
    }

    public static void getTest(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/db_applicationContext.xml"});
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        long userId = 2;
        long orderId = 2;
        Order order = orderService.getOrder(userId, orderId);
        System.out.println(order);
    }
}
