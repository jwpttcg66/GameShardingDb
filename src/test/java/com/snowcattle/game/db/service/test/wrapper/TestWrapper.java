package com.snowcattle.game.db.service.test.wrapper;

import com.snowcattle.game.db.async.AsyncEntityWrapper;
import com.snowcattle.game.db.common.enums.DbOperationEnum;
import com.snowcattle.game.db.service.jdbc.entity.Tocken;
import com.snowcattle.game.db.service.proxy.EnityProxyService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 17/4/6.
 */
public class TestWrapper {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        EnityProxyService enityProxyService = (EnityProxyService) classPathXmlApplicationContext.getBean("enityProxyService");
        Tocken order = new Tocken();
        order.setId("111");
        order.setUserId(222L);
//        Order proxyOrder = enityProxyService.createProxyEntity(order);
        Tocken proxyOrder = order;
        proxyOrder.setStatus("修改了3");

        AsyncEntityWrapper asyncEntityWrapper = new AsyncEntityWrapper(DbOperationEnum.insert);
        String string = asyncEntityWrapper.serialize();
        System.out.println(string);
        AsyncEntityWrapper newAsyncEntityWrapper = new AsyncEntityWrapper();
        newAsyncEntityWrapper.deserialize(string);
        System.out.println(newAsyncEntityWrapper.serialize());

    }
}
