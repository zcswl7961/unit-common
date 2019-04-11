package com.common.util.spring.anno;

import org.springframework.stereotype.Controller;

/**
 * Created by zhoucg on 2019-04-11.
 */
@Controller
public class ControllerExample {

    public void print() {
        System.out.println("这是一个@Controller注解的实体类");
    }
}
