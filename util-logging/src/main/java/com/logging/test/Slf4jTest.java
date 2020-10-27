package com.logging.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoucg
 * @date 2020-10-27 10:13
 */
public class Slf4jTest {


    private static Logger logger = LoggerFactory.getLogger(Slf4jTest.class);


    public static void main(String[] args) {
        logger.info("current test:{}","周");
    }
}
