package com.common.util.disruptor.demo2;

import com.common.util.disruptor.TradeTransaction;
import com.common.util.disruptor.demo1.TradeTransactionInDBHandler;
import com.lmax.disruptor.*;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用WorkerPool创建多个消费群
 * @author: zhoucg
 * @date: 2019-06-26
 */
public class DisruptorWorkerPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        int BUFFER_SIZE=1024;
        int THREAD_NUMBERS=4;

        final RingBuffer<TradeTransaction> ringBuffer = RingBuffer.createSingleProducer(() -> new TradeTransaction(),BUFFER_SIZE,new YieldingWaitStrategy());

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBERS);

        //创建两个消费者，实现至WorkHandler
        WorkHandler<TradeTransaction> workHandlers = event -> System.out.println(Thread.currentThread().getName()+":"+event.getId());
        WorkHandler<TradeTransaction> workHandlers2 = event -> System.out.println(Thread.currentThread().getName()+":"+event.getId());


        //创建一个消费池
        WorkerPool<TradeTransaction> workerPool=new WorkerPool(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), workHandlers,workHandlers2);
        workerPool.start(executor);

        //下面模拟生产数据
        for(int i=0;i<8;i++){
            long seq=ringBuffer.next();
            ringBuffer.get(seq).setPrice(Math.random()*9999);
            ringBuffer.get(seq).setId(UUID.randomUUID().toString()+"="+i);
            ringBuffer.publish(seq);
        }

        Thread.sleep(1000);
        workerPool.halt();
        executor.shutdown();


    }
}
