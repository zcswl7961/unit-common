package com.zcswl.jta.entity;

/**
 * @author zhoucg
 * @date 2021-01-15 14:32
 */
public class OrderItem {

    private Long orderItemId;
    private Long orderId;
    private Integer userId;


    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
