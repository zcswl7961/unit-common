package com.zcswl.lock.redis.support;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Collections;

/**
 * redis lock
 *
 * @author zhoucg
 * @date 2019-11-12 15:23
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisLocked {

    private final JedisSentinelPool pool;

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * expire time units: EX = seconds; PX = milliseconds
     */
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 加锁(非自旋锁)
     * @param lockKey 锁
     * @param requestId 客户端请求标识
     * @param expireTime 加锁时长
     * @return 是否获取成功
     */
    public boolean acquire(String lockKey, String requestId, int expireTime) {
        Jedis jedis = pool.getResource();
        try{
            String result = jedis.set(lockKey,requestId,SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME,expireTime);
            if(LOCK_SUCCESS.equals(result)) {
                return true;
            }
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param lockKey 锁
     * @param requestId 客户端请求标识
     * @return 是否解锁成功
     */
    public boolean release(String lockKey,String requestId) {
        Jedis jedis = pool.getResource();
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey),Collections.singletonList(requestId));
            if(RELEASE_SUCCESS.equals(result)) {
                return true;
            }
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
}
