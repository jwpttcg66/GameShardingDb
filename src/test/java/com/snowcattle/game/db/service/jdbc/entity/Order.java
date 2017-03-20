package com.snowcattle.game.db.service.jdbc.entity;

import com.snowcattle.game.db.entity.BaseEntity;

public class Order extends BaseEntity {
    private int orderId;

    private int userId;

    private String status;


    /**
     * @return order_id
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * @return user_id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}