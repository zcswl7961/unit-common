package com.logging.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhoucg
 * @date 2020-10-27 11:00
 */
public class SpringApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jTest.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        UserBean test = applicationContext.getBean(UserBean.class);
        System.out.println(test.getName());
        LOGGER.info("current name:{}", test.getName());

    }
}
