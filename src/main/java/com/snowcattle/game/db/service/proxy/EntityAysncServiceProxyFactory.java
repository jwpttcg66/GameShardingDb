package com.snowcattle.game.db.service.proxy;

import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.service.entity.EntityService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/3/29.
 */
@Service
public class EntityAysncServiceProxyFactory {
    @Autowired
    private RedisService redisService;

    private EntityAysncServiceProxy createProxy(EntityService EntityService){
        return new EntityAysncServiceProxy<>(redisService);
    }

    private <T extends  EntityService> T  createProxyService(T entityService, EntityAysncServiceProxy entityAysncServiceProxy){
        Enhancer enhancer = new Enhancer();
        //设置需要创建子类的类
        enhancer.setSuperclass(entityService.getClass());
        enhancer.setCallback(entityAysncServiceProxy);
        //通过字节码技术动态创建子类实例
        return (T) enhancer.create();
    }

    public <T extends  EntityService> T createProxyService(T entityService) throws Exception {
        T proxyEntityService = (T) createProxyService(entityService, createProxy(entityService));
        BeanUtils.copyProperties(proxyEntityService, entityService);
        return proxyEntityService;
    }

    public RedisService getRedisService() {
        return redisService;
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
