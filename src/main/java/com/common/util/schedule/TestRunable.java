package com.common.util.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zhoucg on 2019-01-06.
 */
@Component
public class TestRunable implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(TestRunable.class);

    @Override
    public void run() {

        try{
            logger.info("进入到该线程中进行执行");
        }catch (Exception e) {
            logger.error("线程中的信息是错误的");
        }



    }
}
