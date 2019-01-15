package com.common.util.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author zhoucg
 * @date 2018-12-11
 * Spring schedule 动态cron表达式数据配置
 */
@Component
public class MoreThreadSchedule implements SchedulingConfigurer{

    private static final Logger logger = LoggerFactory.getLogger(MoreThreadSchedule.class);

    public static String cron;

    /**
     * 将外部动态配置cron表达式的单线程数据设置在该定时器类的
     * 无参的构造函数下
     */
    public MoreThreadSchedule() {

        //默认情况下，设置每五秒执行一次
        cron = "0/5 * * * * *";

        Runnable runnable = () -> {
            try{
                //外部动态获取
                Thread.sleep(15000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        //修改为：每10秒执行一次.
        cron = "0/10 * * * * *";
        new Thread(runnable).start();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {


//        Runnable localRunnable = () ->{
//            try{
//                logger.info("schedule running ....");
//            } catch ( Exception e) {
//                e.printStackTrace();
//            }
//        };
//        Trigger trigger = (triggerContext) -> {
//
//                //任务触发，可修改任务的执行周期.
//                CronTrigger localtrigger = new CronTrigger(cron);
//                Date nextExec = localtrigger.nextExecutionTime(triggerContext);
//                return nextExec;
//
//        };
//
//        scheduledTaskRegistrar.addTriggerTask(localRunnable, trigger);

    }

    @Bean(destroyMethod="shutdown")
    public Executor setTaskExecutors(){
        return Executors.newScheduledThreadPool(3);//设置三个线程
    }
}
