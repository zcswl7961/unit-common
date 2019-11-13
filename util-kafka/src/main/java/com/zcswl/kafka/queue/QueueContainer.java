package com.zcswl.kafka.queue;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zcswl.kafka.kafka.KafkaProducerConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author zhoucg
 * @date 2019-11-13 9:40
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class QueueContainer {

    private final KafkaProducerConnector connector;

    private final BlockingQueue<String> queue = new LinkedBlockingDeque<>();
    private static final int executorSize = Math.max(1,Runtime.getRuntime().availableProcessors() / 3);
    /**
     * 线程名称
     */
    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("queue-pool-%d").build();
    /**
     * this should not be Rejected
     */
    private static final ExecutorService executorService = new ThreadPoolExecutor(executorSize, executorSize, 0L, MILLISECONDS,new LinkedBlockingDeque<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    @PostConstruct
    public void init() {
        for(int i = 0; i <executorSize ;i++) {
            executorService.submit(new TaskRunner(queue));
        }
    }

    /**
     * 任务添加 入口
     * @param message 消息对列，该消息应该为一个序列化的数据对象
     */
    public void submit(String message) {
        if (queue.contains(message)) {
            log.info("submit task, task exists, queue size:{} task {}", queue.size(), message);
            return ;
        }
        queue.add(message);
    }


    /**
     * 任务队列，不断的从线程池中获取对应的任务数据，并加入到
     */
    private class TaskRunner implements Runnable {
        BlockingQueue<String> queue;
        TaskRunner(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            while(!Thread.currentThread().isInterrupted()) {
                try{
                    String taskInfo = queue.take();
                    connector.send(taskInfo);
                } catch (InterruptedException e) {
                    log.error("taks runner Interrutped,stop,e:{}",e);
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    executorService.submit(new TaskRunner(queue));
                    log.error("task runner catch exception, restart", e);
                }
            }
        }
    }






}
