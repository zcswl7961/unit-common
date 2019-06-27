package com.common.util.disruptor.demo1;

import com.common.util.disruptor.TradeTransaction;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;


/**
 * @author: zhoucg
 * @date: 2019-06-26
 */
public class TradeTransactionInDBHandler implements EventHandler<TradeTransaction>,WorkHandler<TradeTransaction> {
    @Override
    public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(TradeTransaction event) throws Exception {
        //System.out.println(event.getId());
    }


}
