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

    //实体对象
    private IEntity entity;

    //是否需要存储
    private boolean dirtyFlag;

    public EntityProxy(IEntity entity) {
        this.entity = entity;
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


    public IEntity getEntity() {
        return entity;
    }

    public void setEntity(IEntity entity) {
        this.entity = entity;
    }

    public boolean isDirtyFlag() {
        return dirtyFlag;
    }

    public void setDirtyFlag(boolean dirtyFlag) {
        this.dirtyFlag = dirtyFlag;
    }
}
