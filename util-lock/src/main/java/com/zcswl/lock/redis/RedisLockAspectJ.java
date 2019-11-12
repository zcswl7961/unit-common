package com.zcswl.lock.redis;

import com.zcswl.lock.annotation.Lock;
import com.zcswl.lock.redis.support.RedisLocked;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 使用spring的切面编程的方式进行加锁解锁操作
 * @author zhoucg
 * @date 2019-11-12 15:08
 */
@Aspect
@Component
public class RedisLockAspectJ {

    @Autowired
    private RedisLocked redisLocked;

    /**
     * expectTime
     */
    private static final int EXPECT_TIME = 1000 * 60 * 2;

    /**
     * 环绕方法进行加锁解锁操作
     * @param jp 切入点
     * @param ap 注解
     */
    @Around("@annotation(ap)")
    public Object lock(ProceedingJoinPoint jp , Lock ap) throws Throwable {
        /**
         * 切面的传参方法不是我们首先需要去关注的
         */
        jp.getArgs();
        String name = ap.name();
        String requestId = ap.requestId();
        long retryTimes = ap.retryTimes();
        long retryWait = ap.retryWait();
        if("".equals(name)) {
            throw new IllegalArgumentException("lock name must not be null");
        }
        boolean lock = redisLocked.acquire(name,requestId,EXPECT_TIME);
        if(lock) {
            return process(jp,name,requestId);
        } else {
            //重试机制
            if(retryTimes <= 0) {
                throw new IllegalArgumentException("retryTime must be over zero");
            }

            if(retryWait == 0) {
                retryWait = 200;
            }
            // 设置失败次数计数器, 当到达指定次数时, 返回失败
            int failCount = 1;
            while (failCount <= retryTimes) {
                // 等待指定时间ms
                try {
                    Thread.sleep(retryWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (redisLocked.acquire(name,requestId,EXPECT_TIME)) {
                    // 执行主逻辑
                    return process(jp,name,requestId);
                } else {
                    failCount++;
                }
            }
            throw new Exception("error");
        }
    }

    private Object process(ProceedingJoinPoint jp,String name,String requestId) throws Throwable {
        try{
            Object process = jp.proceed();
            return process;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            redisLocked.release(name,requestId);
        }
    }
}
