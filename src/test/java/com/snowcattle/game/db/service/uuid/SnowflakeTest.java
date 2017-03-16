package com.snowcattle.game.db.service.uuid;

/**
 * Created by jiangwenping on 17/2/27.
 */
public class SnowflakeTest {
    public static void main(String[] args) {
        int node = 10;
        Snowflake snowflake = new Snowflake(node);
        System.out.println(snowflake.next());
        System.out.println(snowflake.next());

//        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
//        for (int i = 0; i < 1000; i++) {
//            long id = idWorker.nextId();
//            System.out.println(Long.toBinaryString(id));
//            System.out.println(id);
//        }
//        System.out.println(System.currentTimeMillis());
//        System.out.println(System.nanoTime());

        SnowFlakeUUIDService snowFlakeUUIDService = new SnowFlakeUUIDService(node);
        for (int i = 0; i < 1000; i++) {
            long id = snowFlakeUUIDService.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }

        long maxs = 1 << 38;
        long years = 1000L * 60 * 60 * 24 * 365; //毫秒
        System.out.println(maxs);
        System.out.println(years);
        System.out.println(maxs/years);

        System.out.println((1L << 38) / (1000L * 60 * 60 * 24 * 365));
        System.out.println((1L << 16));
    }
}
