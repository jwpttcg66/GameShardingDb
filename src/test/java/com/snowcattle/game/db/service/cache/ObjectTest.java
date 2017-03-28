package com.snowcattle.game.db.service.cache;

import com.snowcattle.game.db.entity.BaseEntity;
import com.snowcattle.game.db.service.jdbc.entity.Order;

/**
 * Created by jwp on 2017/3/28.
 */
public class ObjectTest {

    public static void main(String[] args) throws InterruptedException {
        int maxSize = 1000000;
        BaseEntity[] baseEntity = new BaseEntity[maxSize];
        for(int i = 0; i < 1000000; i++){
            Order order = new Order();
            order.setId(i);
            order.setUserId(10);
            order.setStatus(String.valueOf(i));
            baseEntity[i] = order;
        }

        Thread.sleep(10000000);
    }
}
