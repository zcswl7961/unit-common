package com.logging.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slf4j是日志接口的门面，api通过对应的Facade设计模式，
 * 其内部只是提供了slf4j-api-version.jar的接口
 * @author zhoucg
 * @date 2020-10-27 10:13
 */
public class Slf4jTest {


    private static Logger logger = LoggerFactory.getLogger(Slf4jTest.class);


    public static void main(String[] args) {
        logger.info("current test:{}","周");
    }
}
