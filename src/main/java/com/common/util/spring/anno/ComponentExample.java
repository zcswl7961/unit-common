package com.common.util.spring.anno;

import org.springframework.stereotype.Component;

/**
 * Created by zhoucg on 2019-04-11.
 */
@Component
public class ComponentExample {

    public void print() {
        System.out.println("基于@Component注解的类");
    }
}
