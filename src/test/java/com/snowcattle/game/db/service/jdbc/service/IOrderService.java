package com.snowcattle.game.db.service.jdbc.service;

import com.snowcattle.game.db.service.jdbc.entity.Order;

import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
public interface IOrderService {
    public int insertOrder(Order order);
    public Order getOrder(long userId, long id);
    public List<Order> getOrderList(long userId);
    void updateOrder(Order order);
    void deleteOrder(Order order);
}

