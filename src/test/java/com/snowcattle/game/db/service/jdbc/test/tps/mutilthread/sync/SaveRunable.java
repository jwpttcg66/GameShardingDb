package com.snowcattle.game.db.service.jdbc.test.tps.mutilthread.sync;

import com.snowcattle.game.db.service.common.uuid.SnowFlakeUUIDService;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.OrderService;
import com.snowcattle.game.db.service.jdbc.test.TestConstants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jwp on 2017/4/18.
 * 执行保存的线程
 */
public class SaveRunable extends  Thread{

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

    /**
     * 开始数量
     */
    private int start;

    private CountDownLatch countDownLatch;

    public SaveRunable(OrderService orderService, SnowFlakeUUIDService snowFlakeUUIDService, AtomicLong count, int start, CountDownLatch countDownLatch) {
        this.orderService = orderService;
        this.snowFlakeUUIDService = snowFlakeUUIDService;
        this.count = count;
        this.start = start;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        int startSize = start;
        int endSize = startSize+TestConstants.saveSize;

        long start = System.currentTimeMillis();
        for (int i = startSize; i < endSize; i++) {
            Order order = new Order();
            order.setId(snowFlakeUUIDService.nextId());
            order.setStatus("测试插入" + i);
            orderService.insertOrder(order);
            count.incrementAndGet();
        }
        long end  = System.currentTimeMillis();

        long time = end - start;
        System.out.println("线程存储" + TestConstants.saveSize + "耗时" + time);
        countDownLatch.countDown();
    }

}
