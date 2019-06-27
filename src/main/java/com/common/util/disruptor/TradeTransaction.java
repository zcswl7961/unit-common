package com.common.util.disruptor;

/**
 * demo中使用的消息假设是一条交易
 * @author: zhoucg
 * @date: 2019-06-26
 */
public class TradeTransaction {

    //交易id
    private String id;
    //交易金额
    private double price;

    public TradeTransaction() {}

    public TradeTransaction(String id,double price){
        super();
        this.id=id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
