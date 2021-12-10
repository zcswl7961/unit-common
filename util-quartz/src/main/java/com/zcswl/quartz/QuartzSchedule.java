package com.zcswl.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;

/**
 * @author xingyi
 * @date 2021/12/10
 */
public class QuartzSchedule {

    public static void main(String[] args) throws SchedulerException, InterruptedException, ParseException {
        // 1 创建调度器Schedule
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(LocalTestJob.class)
                .withIdentity("job1", "group1").build();
        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        //每隔1s执行一次
                        .withIntervalInSeconds(1)
                        //一直执行
                        .repeatForever()).build();

        //4、执行
        JobDetail jobDetail1 = JobBuilder.newJob(CronTriggerJob.class)
                .withIdentity("job2","group2").build();

        // 构建对应的周期调度信息
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName("crontrigger1");
        cronTrigger.setGroup("crongroup");
        cronTrigger.setCronExpression("*/5 * * * * ?");

        scheduler.scheduleJob(jobDetail1, cronTrigger);
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start ! ------------");
        scheduler.start();




        //睡眠
        /*TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");*/
    }
}
