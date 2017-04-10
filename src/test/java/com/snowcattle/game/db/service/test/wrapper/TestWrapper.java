package com.snowcattle.game.db.service.test.wrapper;

import com.snowcattle.game.db.async.AsyncEntityWrapper;
import com.snowcattle.game.db.common.enums.DbOperationEnum;
import com.snowcattle.game.db.service.jdbc.entity.Tocken;
import com.snowcattle.game.db.service.proxy.EnityProxyFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 17/4/6.
 */
public class TestWrapper {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        EnityProxyFactory enityProxyFactory = (EnityProxyFactory) classPathXmlApplicationContext.getBean("enityProxyFactory");
        Tocken tocken = new Tocken();
        tocken.setId("111");
        tocken.setUserId(222L);
        Tocken proxyOrder = enityProxyFactory.createProxyEntity(tocken);
//        Tocken proxyOrder = order;
        proxyOrder.setStatus("修改了3");


        AsyncEntityWrapper asyncEntityWrapper = new AsyncEntityWrapper(DbOperationEnum.insert, tocken.getClass().getSimpleName(),proxyOrder.getEntityProxyWrapper().getEntityProxy().getChangeParamSet());
        String string = asyncEntityWrapper.serialize();
        System.out.println(string);
        AsyncEntityWrapper newAsyncEntityWrapper = new AsyncEntityWrapper();
        newAsyncEntityWrapper.deserialize(string);
        System.out.println(newAsyncEntityWrapper.serialize());

//        Class classes = Class.forName(tocken.getClass().getName());
//        System.out.println(classes);
    }
}
