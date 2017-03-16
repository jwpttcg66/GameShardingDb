package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.common.annotation.MethodSaveProxy;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.util.ObjectUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by jiangwenping on 17/3/16.
 * db存储的代理对象
 */
public class EntityProxy implements MethodInterceptor {

    //代理的队形
    private IEntity entity;

    private Enhancer enhancer = new Enhancer();

    //是否需要存储
    private boolean dirtyFlag;

    public IEntity getProxy(IEntity entity){
        //设置需要创建子类的类
        enhancer.setSuperclass(entity.getClass());
        enhancer.setCallback(this);
        this.entity = entity;
        //通过字节码技术动态创建子类实例
        return (IEntity) enhancer.create();
    }

    //实现MethodInterceptor接口方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        //通过代理类调用父类中的方法
        Object result = null;
        //检查MethodProxy注解
        MethodSaveProxy methodSaveProxyAnnotation = (MethodSaveProxy) method
                .getAnnotation(MethodSaveProxy.class);
        if (methodSaveProxyAnnotation != null) {
            //检查对象原来数值
            String filedName = methodSaveProxyAnnotation.proxy();
            Object oldObject = ObjectUtils.getFieldsValueObj(entity, filedName);
//            System.out.println(oldObject);
            //获取新参数
            Object newObject = args[0];
            result = proxy.invokeSuper(obj, args);

            if(oldObject == null){
                dirtyFlag = true;
            }else if(!oldObject.equals(newObject)){
                dirtyFlag = true;
            }

            if(dirtyFlag){
                System.out.println("数据变换");
            }
        }else{
            result = proxy.invokeSuper(obj, args);
        }

        return result;
    }
}
