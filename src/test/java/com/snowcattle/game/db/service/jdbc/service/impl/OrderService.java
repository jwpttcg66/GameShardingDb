package com.snowcattle.game.db.service.jdbc.service.impl;

import com.snowcattle.game.db.service.entity.EntityKeyShardingStrategyEnum;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.mapper.IDBMapper;
import com.snowcattle.game.db.service.jdbc.mapper.OrderMapper;
import com.snowcattle.game.db.service.jdbc.service.IOrderService;
import com.snowcattle.game.db.service.proxy.EntityProxyWrapper;
import com.snowcattle.game.db.sharding.CustomerContextHolder;
import com.snowcattle.game.db.sharding.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangwenping on 17/3/20.
 */
@Service
public class OrderService extends EntityService<Order> implements IOrderService{


    public int insertOrder(Order order) {
        return insertEntity(order);
    }

    @Override
    public Order getOrder(long userId, long id) {
        Order order = new Order();
        order.setUserId(userId);
        order.setId(id);
        return (Order) getEntity(order);
    }

    @Override
    public List<Order> getOrderList(long userId) {
        Order order = new Order();
        order.setUserId(userId);
        return getEntityList(order);
    }

    @Override
    public void updateOrder(Order order) {
        updateEntity(order);
    }

    @Override
    public void deleteOrder(Order order) {
        deleteEntity(order);
    }
}
