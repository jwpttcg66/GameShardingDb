package com.snowcattle.game.db.sharding;

import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/3/6.
 */
@Service
public class CustomerContextHolder {

    private static  final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    private static int DB_COUNT=3;
    private static int TABLE_COUNT=2;
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

    public  static String getShardingDBKeyByUserId(DataSourceType dataSourceType, int userId) {
        int dbIndex = userId % DB_COUNT;
        return dataSourceType.toString() + dbIndex;
    }

    public static int getShardingDBTableIndexByUserId(int userId){
        return userId%TABLE_COUNT;
    }
}
