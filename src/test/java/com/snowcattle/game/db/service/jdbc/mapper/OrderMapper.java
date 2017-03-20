package com.snowcattle.game.db.service.jdbc.mapper;


import com.snowcattle.game.db.service.jdbc.entity.Order;

/**
 * Created by jiangwenping on 17/3/6.
 */
public interface OrderMapper {
    int insertOrder(Order order);
    Order getOrder(int sharding_table_index, long userId,long orderId);
}
