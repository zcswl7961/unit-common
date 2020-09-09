package com.common.guava;

import com.google.common.eventbus.Subscribe;

/**
 *
 * Event Bus 消息处理线程
 * @author zhoucg
 * @date 2020-09-09 9:59
 */
public class OrderEventListener {


    /**
     * 如果发送了OrderMessage消息，会进入到该函数的处理
     * @param event 消息
     */
    @Subscribe
    public void dealWithEvent(OrderMessage event) {
        System.out.println("我收到了您的命令，命令内容为：" + event.getOrderContent());
    }
}
