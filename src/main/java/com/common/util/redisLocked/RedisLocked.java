package com.common.util.redisLocked;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>基于jedis实现的分布式锁,适用基于redis setnx()命令进行创建
 *
 * <p>在下行采集基于集群化的部署策略中，每一个集群的节点不应该单独的处理一份数据并上传到日志库，
 *    各个集群的节点中要形成关联，共享协同处理当前下行数据
 * <p>在下行采集告警中，每一次形成采集的指标数据会被推送到Advisor中，共享处理
 * @author: zhoucg
 * @date: 2019-07-22
 */
public class RedisLocked {

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     * expire time units: EX = seconds; PX = milliseconds
     */
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections
                .singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++) {
            Runnable runnable = () -> {
                JedisPoolConfig config = new JedisPoolConfig();
                Set<String> sentinels = new HashSet();
                config.setMaxTotal(200);
                config.setMaxWaitMillis(1000);
                config.setMaxIdle(30);
                config.setMinIdle(10);
                String[] ipaddrs = "10.1.241.37:26379".split(",");
                for (String ipaddr : ipaddrs) {
                    String[] arrays = ipaddr.split(":");
                    sentinels.add(new HostAndPort(arrays[0], Integer.parseInt(arrays[1])).toString());
                }
                JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, config, 2000, "Root_123");

                Jedis jedis = pool.getResource();
                boolean isLocked = tryGetDistributedLock(jedis,"redis_12","12",20000);
                System.out.println("当前线程获取锁信息:"+Thread.currentThread().getName()+" 获取锁信息："+isLocked);
                releaseDistributedLock(jedis,"redis_12","12");
            };
            executorService.submit(runnable);
        }

    }
}
