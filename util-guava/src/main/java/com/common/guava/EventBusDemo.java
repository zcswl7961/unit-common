package com.common.guava;

import com.google.common.eventbus.EventBus;

/**
 * https://blog.csdn.net/wuyuxing24/article/details/95505102
 *
 * 异步消息处理 AsyncEventBus：
 * AsyncEventBus是继承EventBus异步消息，准确来讲是我们可以指定消息的处理在哪里执行。
 * 比如我们可以把他们都放到一个线程池里面去执行。
 *
 * @author zhoucg
 * @date 2020-09-09 9:57
 */
public class EventBusDemo {


    public static void main(String[] args) {

        // 创建一个EventBus对象
        EventBus eventBus = new EventBus("test");

        // 注册监听器
        eventBus.register(new OrderEventListener());

        // 注册事件
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrderContent("ZJOKLS");
        eventBus.post(orderMessage);
    }
}
