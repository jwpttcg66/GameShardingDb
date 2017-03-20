package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.entity.IEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/3/16.
 */
@Service
public class DbProxyService{

    private EntityProxy createProxy(IEntity entity){
        return new EntityProxy(entity);
    }

    private BaseEntity createProxyEntity(EntityProxy entityProxy){
        Enhancer enhancer = new Enhancer();
        //设置需要创建子类的类
        enhancer.setSuperclass(entityProxy.getEntity().getClass());
        enhancer.setCallback(entityProxy);
        //通过字节码技术动态创建子类实例
        return (BaseEntity) enhancer.create();
    }

    public <T > T initProxyWrapper(BaseEntity entity) throws Exception {
        EntityProxy entityProxy = createProxy(entity);
        EntityProxyWrapper entityProxyWrapper = new EntityProxyWrapper(entityProxy);
        BaseEntity proxyEntity = createProxyEntity(entityProxy);
        //注入对象 数值
        BeanUtils.copyProperties(proxyEntity,entity);
        entityProxy.setCollectFlag(true);
        proxyEntity.setEntityProxyWrapper(entityProxyWrapper);
        return (T) proxyEntity;
    }
}
