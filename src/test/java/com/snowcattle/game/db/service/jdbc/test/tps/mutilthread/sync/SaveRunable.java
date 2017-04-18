package com.snowcattle.game.db.service.jdbc.test.tps.mutilthread.sync;

import com.snowcattle.game.db.service.common.uuid.SnowFlakeUUIDService;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.OrderService;
import com.snowcattle.game.db.service.jdbc.test.TestConstants;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jwp on 2017/4/18.
 * 执行保存的线程
 */
public class SaveRunable implements  Runnable{

    /**
     * 订单服务
     */
    private OrderService orderService;

    /**
     * id服务
     */
    private SnowFlakeUUIDService snowFlakeUUIDService;

    /**
     * 数量
     */
    private AtomicLong count;


    @Override
    public void run() {
        int startSize = TestConstants.batchStart;
        int endSize = TestConstants.batchStart+TestConstants.saveSize;

        long start = System.currentTimeMillis();
        for (int i = startSize; i < endSize; i++) {
            Order order = new Order();
            order.setId(snowFlakeUUIDService.nextId());
            order.setStatus("测试插入" + i);
            orderService.insertOrder(order);
        }
        long end  = System.currentTimeMillis();

        long time = end - start;
        System.out.println("存储" + TestConstants.saveSize + "耗时" + time);
    }

}
