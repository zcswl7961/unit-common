package com.common.util.disruptor.demo3;


import com.common.util.disruptor.TradeTransaction;
import com.lmax.disruptor.EventHandler;

/**
 * @author: zhoucg
 * @date: 2019-06-26
 */
public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {
    @Override
    public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
        //do Something
        //System.out.println("TradeTransactionVasConsumer:"+event.getId()+":"+event.getPrice()+" sequence:"+sequence+" endOfBatch:"+endOfBatch);
    }
}
