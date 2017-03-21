package com.snowcattle.game.db.service.jdbc.service.impl;

import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.mapper.OrderMapper;
import com.snowcattle.game.db.service.jdbc.service.IOrderService;
import com.snowcattle.game.db.service.proxy.EntityProxyWrapper;
import com.snowcattle.game.db.sharding.CustomerContextHolder;
import com.snowcattle.game.db.sharding.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangwenping on 17/3/20.
 */
@Service
public class OrderService extends EntityService<Order> implements IOrderService{

    @Autowired
    private OrderMapper orderMapper;

    public int insertOrder(Order order) {
        return insertEntity(orderMapper, order);
    }

    @Override
    public Order getOrder(long userId, long orderId) {
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, userId));
        int sharding_table_index = CustomerContextHolder.getShardingDBTableIndexByUserId(userId);
        Map hashMap = new HashMap<>();
        hashMap.put("sharding_table_index", sharding_table_index);
        hashMap.put("userId", userId);
        hashMap.put("orderId", orderId);
        return orderMapper.getOrder(hashMap);
    }

    @Override
    public void updateOrder(Order order) {
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, order.getUserId()));
        int sharding_table_index = CustomerContextHolder.getShardingDBTableIndexByUserId(order.getUserId());
        Map hashMap = new HashMap<>();
        hashMap.put("sharding_table_index", sharding_table_index);
        hashMap.put("userId", order.getUserId());
        hashMap.put("orderId", order.getOrderId());
        EntityProxyWrapper entityProxyWrapper = order.getEntityProxyWrapper();
        if(entityProxyWrapper != null){
            hashMap.putAll(entityProxyWrapper.getEntityProxy().getChangeParamSet());
        }
        orderMapper.updateOrderByMap(hashMap);
    }

    @Override
    public void deleteOrder(Order order) {
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, order.getUserId()));
        order.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(order.getUserId()));
        orderMapper.deleteOrder(order);
    }
}
