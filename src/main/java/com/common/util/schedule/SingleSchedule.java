package com.common.util.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhoucg on 2018-12-11.
 */
@Component
public class SingleSchedule {

    private static final Logger logger = LoggerFactory.getLogger(SingleSchedule.class);

    /**
     * 单线程线程池，控制顺序执行
     */
    private static volatile ExecutorService singleThreadPool = null;

    @Autowired
    private TestRunable testRunable;


    @Scheduled(cron = "*/10 * * * * ?")
    public void init() {

        if(singleThreadPool == null) {
            singleThreadPool = Executors.newSingleThreadExecutor();
        }

        Thread thread = new Thread(testRunable);

        singleThreadPool.execute(thread);

//        singleThreadPool.execute(() -> {
//            logger.info("进入到线程池中");
//            try{
//                Thread.sleep(20000);
//            }catch(Exception e) {
//                logger.error("错误");
//            }
//
//        });
    }
    @Scheduled(cron = "*/10 * * * * ?")
    public void reInit() {

        //System.out.println("线程执行");

    }
}
