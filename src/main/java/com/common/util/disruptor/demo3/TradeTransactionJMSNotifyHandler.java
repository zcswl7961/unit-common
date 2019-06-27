package com.common.util.disruptor.demo3;


import com.common.util.disruptor.TradeTransaction;
import com.lmax.disruptor.EventHandler;

/**
 * https://my.oschina.net/pvpCC9IFwqz4/blog/507735
 *  1、交易网关收到交易(P1)把交易数据发到RingBuffer中，
    2、负责处理增值业务的消费者C1和负责数据存储的消费者C2负责处理交易
    3、负责发送JMS消息的消费者C3在C1和C2处理完成后再进行处理。
 * @author: zhoucg
 * @date: 2019-06-26
 */
public class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction> {
    @Override
    public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
        // do send jms message，发送信息到消息队列中
        System.out.println(event.getId()+":"+event.getPrice()+" sequence:"+sequence+" endOfBatch:"+endOfBatch);
    }
}
