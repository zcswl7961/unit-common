package com.common.util.spring.anno;

import org.springframework.stereotype.Service;

/**
 * Created by zhoucg on 2019-04-11.
 */
@Service
public class ServiceExample {


    public void printService() {
        System.out.println("基于@Service 注解的类");
    }
}
