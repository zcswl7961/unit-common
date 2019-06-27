package com.common.util.disruptor.demo1;

import com.common.util.disruptor.TradeTransaction;
import com.lmax.disruptor.*;

import java.util.UUID;
import java.util.concurrent.*;

/**
 *  <dependency>
        <groupId>com.lmax</groupId>
        <artifactId>disruptor</artifactId>
        <version>3.2.1</version>
    </dependency>
 *  Disruptor是一个高性能的异步处理框架,或者可以认为是线程间通信的高效低延时的内存消息组件，
 *  它最大特点是高性能，其LMAX架构可以获得每秒6百万订单，用1微秒的延迟获得吞吐量为100K+。
 *
 *
 *  使用原生的API创建一个简单的生产者和消费者数据
 *
 *
 * @author: zhoucg
 * @date: 2019-06-25
 */
public class DisruptorDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int BUFFER_SIZE=1024;
        int THREAD_NUMBERS=4;

        final RingBuffer<TradeTransaction> ringBuffer = RingBuffer.createSingleProducer(() -> new TradeTransaction(),BUFFER_SIZE,new YieldingWaitStrategy());

        //创建线程池
        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMBERS);
        //创建SequenceBarrier
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();


        //创建消息处理器
        BatchEventProcessor<TradeTransaction> transProcessor = new BatchEventProcessor<>(ringBuffer,sequenceBarrier,new TradeTransactionInDBHandler());

        //这一部的目的是让RingBuffer根据消费者的状态    如果只有一个消费者的情况可以省略
        ringBuffer.addGatingSequences(transProcessor.getSequence());
        executors.submit(transProcessor);

        //如果存大多个消费者 那重复执行上面3行代码 把TradeTransactionInDBHandler换成其它消费者类

        Future<?> future=executors.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                long seq;
                for(int i=0;i<1000;i++){
                    seq=ringBuffer.next();//占个坑 --ringBuffer一个可用区块

                    ringBuffer.get(seq).setPrice(Math.random()*9999);//给这个区块放入 数据  如果此处不理解，想想RingBuffer的结构图
                    ringBuffer.get(seq).setId(UUID.randomUUID().toString()); //
                    ringBuffer.publish(seq);//发布这个区块的数据使handler(consumer)可见
                }
                return null;
            }
        });
        future.get();//等待生产者结束
        Thread.sleep(1000);//等上1秒，等消费都处理完成
        transProcessor.halt();//通知事件(或者说消息)处理器 可以结束了（并不是马上结束!!!）
        executors.shutdown();//终止线程

    }
}
