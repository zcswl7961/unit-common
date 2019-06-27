package com.common.util.activeMQ;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: zhoucg
 * @date: 2019-06-25
 */
public class Producter {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("MQApplicationContext.xml");
    }
}
