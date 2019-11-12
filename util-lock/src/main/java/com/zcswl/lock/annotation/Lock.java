package com.zcswl.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis分布式锁的注解
 *
 * <p>redis分布式加锁实现依赖于jedis.set(key,requestId,nx,px,expireTime) 原子语句进行实现
 *      <ul>
 *          <li>key:第一个参数表示的是锁的名称，这个是具有的业务逻辑性，保证唯一性</li>
 *          <li>requestId:第二个参数是请求的requestId，这样做的目的主要是为了保证加解锁的唯一性。这样我们就可以知道该锁是哪个客户端加的.</li>
 *          <li>NX:第三个参数表示NX，表示当key不存在时我们才进行set操作</li>
 *          <li>PX:第四个参数表示时间戳，</li>
 *          <li>expireTime:第五个参数表示具体的过期时间</li>
 *      </ul>
 * <p>redis 分布式锁的解锁过程使用lua的原语主要时为了保证原子性，redis的eval可以保证原子性
 * @author zhoucg
 * @date 2019-11-12 15:05
 * @see com.zcswl.lock.redis.support.RedisLocked
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
