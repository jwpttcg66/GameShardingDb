package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.entity.IEntity;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/3/16.
 */
@Service
public class DbProxyService {

    private EntityProxy createProxy(IEntity entity){
        return new EntityProxy(entity);
    }

    private IEntity createProxyEntity(EntityProxy entityProxy){
        Enhancer enhancer = new Enhancer();
        //设置需要创建子类的类
        enhancer.setSuperclass(entityProxy.getEntity().getClass());
        enhancer.setCallback(entityProxy);
        //通过字节码技术动态创建子类实例
        return (IEntity) enhancer.create();
    }

    public EntityProxyWrapper createEntityProxyWrapper(BaseEntity entity){
        EntityProxy entityProxy = createProxy(entity);
        IEntity proxyEntity = createProxyEntity(entityProxy);
        return new EntityProxyWrapper((BaseEntity) proxyEntity, entityProxy);
    }

    public <T > T initProxyWrapper(Class<T> entityClass,  BaseEntity entity){
        EntityProxyWrapper entityProxyWrapper = createEntityProxyWrapper(entity);
        BaseEntity proxyEntity = entityProxyWrapper.getProxyEntity();
        proxyEntity.setEntityProxyWrapper(entityProxyWrapper);

        //注入对象 数值

        return (T) entityProxyWrapper.getProxyEntity();
    }
}
