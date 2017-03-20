package com.snowcattle.game.db.service.jdbc.service.impl;

import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.mapper.OrderMapper;
import com.snowcattle.game.db.sharding.CustomerContextHolder;
import com.snowcattle.game.db.sharding.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/3/20.
 */
@Service
public class OrderService{

    @Autowired
    private OrderMapper orderMapper;

    public int insertOrder(Order order) {
        CustomerContextHolder.setCustomerType(CustomerContextHolder.getShardingDBKeyByUserId(DataSourceType.jdbc_player_db, order.getUserId()));
        order.setSharding_table_index(CustomerContextHolder.getShardingDBTableIndexByUserId(order.getUserId()));
        return orderMapper.insertOrder(order);
    }
}
