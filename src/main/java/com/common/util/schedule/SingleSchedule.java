package com.common.util.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by zhoucg on 2018-12-11.
 */
@Component
public class SingleSchedule {

    private static final Logger logger = LoggerFactory.getLogger(SingleSchedule.class);


    @Scheduled(cron = "*/10 * * * * ?")
    public void init() {
        logger.info("schedule running ....");
    }
}
