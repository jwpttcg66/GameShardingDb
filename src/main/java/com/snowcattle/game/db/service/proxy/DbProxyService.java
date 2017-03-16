package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.entity.IEntity;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/3/16.
 */
@Service
public class DbProxyService {

    public EntityProxy createProxy(IEntity entity){
        return new EntityProxy(entity);
    }

    public IEntity getProxyEntity(EntityProxy entityProxy){
        Enhancer enhancer = new Enhancer();
        //设置需要创建子类的类
        enhancer.setSuperclass(entityProxy.getEntity().getClass());
        enhancer.setCallback(entityProxy);
        //通过字节码技术动态创建子类实例
        return (IEntity) enhancer.create();
    }
}
