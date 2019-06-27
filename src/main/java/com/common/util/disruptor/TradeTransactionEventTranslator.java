package com.common.util.disruptor;

import com.lmax.disruptor.EventTranslator;

import java.util.Random;
import java.util.UUID;

/**
 * @author: zhoucg
 * @date: 2019-06-26
 */
public class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction>{
    private Random random=new Random();

    @Override
    public void translateTo(TradeTransaction event, long sequence) {
        this.generateTradeTransaction(event);
    }

    private TradeTransaction generateTradeTransaction(TradeTransaction trade){
        trade.setId(UUID.randomUUID().toString());
        trade.setPrice(random.nextDouble()*9999);
        return trade;
    }


}
