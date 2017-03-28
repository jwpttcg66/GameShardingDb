package com.snowcattle.game.db.sharding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/3/6.
 */
@Service
public class CustomerContextHolder {

    private static  final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    @Autowired
    private static int db_count;

    @Autowired
    private static int table_count;

    public  static String getCustomerType() {
        return (String) contextHolder.get();
    }
    /**
     * 通过字符串选择数据源
     * @param customerType
     */
    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }

    public  static String getShardingDBKeyByUserId(DataSourceType dataSourceType, long userId) {
        long dbIndex = userId % db_count;
        return dataSourceType.toString() + dbIndex;
    }

    public static int getShardingDBTableIndexByUserId(long userId){
        return (int) (userId%table_count);
    }

    public static int getDb_count() {
        return db_count;
    }

    public static void setDb_count(int db_count) {
        CustomerContextHolder.db_count = db_count;
    }

    public static int getTable_count() {
        return table_count;
    }

    public static void setTable_count(int table_count) {
        CustomerContextHolder.table_count = table_count;
    }
}
