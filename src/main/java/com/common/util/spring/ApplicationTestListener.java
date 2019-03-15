package com.common.util.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by zhoucg on 2019-03-15.
 */
public class ApplicationTestListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ApplicationTestEvent) {
            ((ApplicationTestEvent) event).print();
        }
    }
}
