package com.snowcattle.game.db.service.jdbc.service;

import com.snowcattle.game.db.service.jdbc.entity.MoreOrder;

import java.util.List;

/**
 * Created by jiangwenping on 17/4/5.
 */
public interface IMoreOrderService {
    public List<MoreOrder> getOrderList(MoreOrder moreOrder);
}
