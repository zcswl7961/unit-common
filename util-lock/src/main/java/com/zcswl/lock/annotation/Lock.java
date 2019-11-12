package com.zcswl.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis分布式锁的注解
 * @author zhoucg
 * @date 2019-11-12 15:05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

    /**
     * 当前全局分布式锁的锁名称
     */
    String name() default "";

    /**
     * 重试次数
     */
    int retryTimes() default 0;

    /**
     * 重试的间隔时间
     */
    long retryWait() default 0L;

    /**
     * 当前任务线程锁获取的requestId
     * @return
     */
    String requestId() default "";
}
