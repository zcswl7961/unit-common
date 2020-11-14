package com.zcswl.spring.util;

import org.springframework.util.StopWatch;

/**
 * @author zhoucg
 * @date 2020-10-30 14:38
 */
public class StopWatchTest {


    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch("zhoucg");
        stopWatch.start();

        Thread.sleep(10000);

        stopWatch.stop();

        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        StopWatch.TaskInfo lastTaskInfo = stopWatch.getLastTaskInfo();

        stopWatch.start();
        Thread.sleep(1000);
        stopWatch.stop();


        StopWatch.TaskInfo[] taskInfo = stopWatch.getTaskInfo();
        String lastTaskName = stopWatch.getLastTaskName();
    }
}
