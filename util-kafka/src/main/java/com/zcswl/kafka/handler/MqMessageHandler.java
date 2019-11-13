package com.zcswl.kafka.handler;

/**
 * 消费者消息处理
 * @author zhoucg
 * @date 2019-11-13 10:39
 */
public interface MqMessageHandler {

    /**
     * 消息处理
     * @param message
     */
    void handle(String message);
}
