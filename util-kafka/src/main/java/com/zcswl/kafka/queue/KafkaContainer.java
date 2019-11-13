package com.zcswl.kafka.queue;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zcswl.kafka.annotation.LogInterceptor;
import com.zcswl.kafka.common.SpringContextUtil;
import com.zcswl.kafka.handler.PreHandler;
import com.zcswl.kafka.kafka.KafkaProducerConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author zhoucg
 * @date 2019-11-13 9:40
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaContainer {

    private static Predicate<String> preHandlerPredicate = preHandlerName -> SpringContextUtil.findAnnotationOnBean(preHandlerName, LogInterceptor.class) != null;

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
     * 在这里，添加了一层Queue缓存，主要的目的是做业务层数据缓存去重操作（这种去重操作一般是由于数据队列过程，并且线程处理不过来数据导致）
     *
     * @param message 消息对列，该消息应该为一个序列化的数据对象
     */
    public void submit(String message) {
        Map<String, PreHandler> handlers = SpringContextUtil.getBeansOfType(PreHandler.class);
        if(null!= handlers && handlers.size() != 0) {
            Set<String> handlerNames = handlers.keySet();
            List<String> orderLists = handlerNames.stream().filter(preHandlerPredicate).collect(Collectors.toList());
            Collections.sort(orderLists, (o1, o2) -> {
                LogInterceptor o1Order = SpringContextUtil.findAnnotationOnBean(o1,LogInterceptor.class);
                LogInterceptor o2Order = SpringContextUtil.findAnnotationOnBean(o2,LogInterceptor.class);
                if(o1Order.order() < o2Order.order()) {
                    return 1;
                } else {
                    return -1;
                }
            });
            final List<PreHandler> orderPreHandlers = Lists.newArrayList();
            orderLists.forEach(orderName -> {
                handlerNames.remove(orderName);
                orderPreHandlers.add(handlers.get(orderName));
            });
            //无排序
            handlerNames.forEach(notOrderName -> orderPreHandlers.add(handlers.get(notOrderName)));
            for(PreHandler handler : orderPreHandlers) {
                if(message == null) {
                    log.info("preHandler handler this message ，final return null,now old message:{}",message);
                    return;
                }
                message = handler.preHandler(message);
            }
        }
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
