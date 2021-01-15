package com.zcswl.jta.entity;

/**
 * @author zhoucg
 * @date 2021-01-15 14:23
 */
public class Order {

    private Long orderId;

    private Integer userId;

    private String status;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
