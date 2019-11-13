package com.zcswl.kafka.handler;

/**
 * 消息预存处理，在这一部中，我们预留了最后一部对于message消息的处理。
 *
 * <p>通过与{@link com.zcswl.kafka.annotation.LogInterceptor} LogInteceptor相集合，通过解析容器中注入进去的PreHandler，我们
 * 解析对应的处理流程，并按照一定顺序进行执行。
 *
 * <p>在进入到kafka队列之前，我们会经过一系列的PreHandler处理，并最终获取到合适的message，如果返回不为null，最终加入到kafka消息队列
 *
 * @author zhoucg
 * @date 2019-11-13 17:18
 * @see com.zcswl.kafka.queue.KafkaContainer
 * @see com.zcswl.kafka.annotation.LogInterceptor
 */
public interface PreHandler {

    /**
     * 消息预处理
     * @param message 原数据
     * @return 处理之后的数据
     */
    String preHandler(String message);
}
