package com.zcswl.lock.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 配置中心，获取application配置信息中redis的相关配置
 *
 * <p>redis.sentinel.nodes,redis.sentinel.masterName,redis.sentinel.password
 *
 * <p>将配置解析到的{@link JedisSentinelPool} pool 加入到spring 容器中
 * @author zhoucg
 * @date 2019-11-12 15:26
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "redis.sentinel")
public class JedisConfig {

    /**
     * redis sentinel 节点数据
     */
    private String nodes;

    /**
     * redis sentinel masterName信息
     */
    private String masterName;

    /**
     * redis sentinel 密码信息
     */
    private String password;

    /**
     * 对应的拆分符号
     */
    private static final String COMMA = ",";

    /**
     * 对应的每一个redis sentinal 配置数据
     */
    private static final String SEMICOLON = ":";


    /**
     * register JedisSentinelPool into ApplicationContext
     */
    @Bean
    public JedisSentinelPool getJedisSentinelPool() {
        JedisSentinelPool pool;
        int timeout = 2000;
        Set<String> sentinels = new HashSet();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxWaitMillis(1000);
        config.setMaxIdle(30);
        config.setMinIdle(10);
        String[] ipaddrs = nodes.split(COMMA);
        for(String ipaddr : ipaddrs) {
            String[] arrays = ipaddr.split(SEMICOLON);
            sentinels.add(new HostAndPort(arrays[0], Integer.parseInt(arrays[1])).toString());
        }
        try{
            pool = new JedisSentinelPool(masterName,sentinels,config,timeout,password);
        } catch (Exception e) {
            log.error("current get JedisSentinelPool error, error:{}",e);
            pool = null;
        }
        return pool;
    }


}
