package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.service.entity.EntityService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Created by jwp on 2017/3/23.
 * 实体存储服务代理服务
 */
public class EntityServiceProxyService {

    private EntityServiceProxy createProxy(EntityService EntityService){
        return new EntityServiceProxy<>();
    }

    private <T extends  EntityService> T  createProxyEntity(EntityServiceProxy entityServiceProxy){
        Enhancer enhancer = new Enhancer();
        //设置需要创建子类的类
        enhancer.setSuperclass(entityServiceProxy.getClass());
        enhancer.setCallback(entityServiceProxy);
        //通过字节码技术动态创建子类实例
        return (T) enhancer.create();
    }

    public <T extends  EntityService> T createProxyServiceEntity(T entityService) throws Exception {
        return (T) createProxyEntity(createProxy(entityService));
    }
}
