package com.common.util.disruptor.demo3;

import com.common.util.disruptor.*;
import com.common.util.disruptor.demo1.TradeTransactionInDBHandler;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: zhoucg
 * @date: 2019-06-26
 */
public class Demo3 {

    public static void main(String[] args) throws InterruptedException {
        long beginTime=System.currentTimeMillis();
        int bufferSize=1024;
        ExecutorService executor= Executors.newFixedThreadPool(8);
        Disruptor<TradeTransaction> disruptor=new Disruptor<TradeTransaction>(() -> new TradeTransaction(), bufferSize, executor, ProducerType.SINGLE, new BusySpinWaitStrategy());
        //使用disruptor创建消费者组C1,C2
        //TradeTransactionVasConsumer实现EventHandler，EventHandler是能够对于event时间处理的过程进行感知
        EventHandlerGroup<TradeTransaction> handlerGroup=disruptor.handleEventsWith(new TradeTransactionVasConsumer(),new TradeTransactionInDBHandler());
        TradeTransactionJMSNotifyHandler jmsConsumer=new TradeTransactionJMSNotifyHandler();
        //声明在C1,C2完事之后执行JMS消息发送操作 也就是流程走到C3
        handlerGroup.then(jmsConsumer);




        disruptor.start();//启动
        CountDownLatch latch=new CountDownLatch(1);
        //生产者准备
        executor.submit(new TradeTransactionPublisher(latch, disruptor));
        latch.await();//等待生产者完事.
        disruptor.shutdown();
        executor.shutdown();

        System.out.println("总耗时:"+(System.currentTimeMillis()-beginTime) / 1000);
    }

    static class TradeTransactionPublisher implements Runnable{
        Disruptor<TradeTransaction> disruptor;
        private CountDownLatch latch;

        private static int LOOP=10000000;//模拟一千万次交易的发生

        public TradeTransactionPublisher(CountDownLatch latch,Disruptor<TradeTransaction> disruptor) {
            this.disruptor=disruptor;
            this.latch=latch;
        }

        @Override
        public void run() {
            TradeTransactionEventTranslator tradeTransloator=new TradeTransactionEventTranslator();
            for(int i=0;i<LOOP;i++){
                disruptor.publishEvent(tradeTransloator);
            }
            latch.countDown();
        }
    }

    static class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
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


    private static Integer INDEX = 0;

    private static RingBuffer<TradeTransaction> ringBuffer;

    /**
     * 另一种
     */
    public static void testAnother() {
        int bufferSize=1024;
        int threadNum = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum,r -> {
            String name = "current Thread Name";
            if(threadNum > 1) {
                name = name + INDEX--;
            }
            Thread thread = new Thread(r,name);
            thread.setDaemon(true);
            return thread;
        });

        Disruptor<TradeTransaction> disruptor = new Disruptor<TradeTransaction>(()-> new TradeTransaction(),bufferSize,executorService);


        //1，绑定消息消费者
        int consumerCount = 8;
        WorkHandler<TradeTransaction>[] workHandlers = new WorkHandler[consumerCount];
        for(int i=0;i<consumerCount;i++) {
            workHandlers[i] = event -> System.out.println(event.getId());
        }
        disruptor.handleEventsWithWorkerPool(workHandlers);

        //2，使用EventHandlerGroup进行Diruptor绑定消费者群
        //使用disruptor创建消费者组C1,C2
        //TradeTransactionVasConsumer实现EventHandler，EventHandler是能够对于event时间处理的过程进行感知
        EventHandlerGroup<TradeTransaction> handlerGroup=disruptor.handleEventsWith(new TradeTransactionVasConsumer(),new TradeTransactionInDBHandler());
        TradeTransactionJMSNotifyHandler jmsConsumer=new TradeTransactionJMSNotifyHandler();
        //声明在C1,C2完事之后执行JMS消息发送操作 也就是流程走到C3
        handlerGroup.then(jmsConsumer);

        ringBuffer = disruptor.getRingBuffer();



        //开启disruptor
        disruptor.start();
     }

    /**
     * 生产者推送数据
     */
    public static void publish(TradeTransaction tradeTransaction) {
        if(tradeTransaction != null) {
            long next = ringBuffer.next();
            try{
                TradeTransaction tradeTransaction1 = ringBuffer.get(next);
                tradeTransaction.setPrice(tradeTransaction.getPrice());
                tradeTransaction.setId(tradeTransaction.getId());
            } finally {
                ringBuffer.publish(next);
            }
        }
    }
}
