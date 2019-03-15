package com.common.util.spring;

import org.springframework.context.ApplicationEvent;

/**
 * Created by zhoucg on 2019-03-15.
 */
public class ApplicationTestEvent extends ApplicationEvent{

    public String msg;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ApplicationTestEvent(Object source) {
        super(source);
    }

    public ApplicationTestEvent(Object source,String msg) {
        super(source);
        this.msg = msg;
    }

    public void print() {
        System.out.println(msg);
    }
}
