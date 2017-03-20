package com.snowcattle.game.db.service.jdbc.service;

import com.snowcattle.game.db.service.jdbc.entity.Order;

/**
 * Created by jiangwenping on 17/3/20.
 */
public interface IOrderService {
    public int insertOrder(Order order);
}

