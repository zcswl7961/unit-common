package com.zcswl.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * quartz job原生
 * @author xingyi
 * @date 2021/12/10
 */
public class LocalTestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println("LocalTestJob start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100));
    }
}
