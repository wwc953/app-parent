package com.example.apputils.redis.jedis.impl;

import com.example.apputils.redis.api.IRedisService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/29 14:57
 */
@Configuration
@ConditionalOnExpression("('${redis.implementation.type:spring}'.equals('jedis')) and !T(org.springframework.util.StringUtils).isEmpty('${spring.redis.cluster.nodes:}')")
@EnableConfigurationProperties({RedisProperties.class})
public class JedisClusterConfiguration {
    private static final int DEFAULT_TIMEOUT = 6000;

    public JedisClusterConfiguration() {

    }

    @Bean
    public IRedisService jedisClusterRedisServiceImpl() {
        return new JedisClusterRedisServiceImpl();
    }

    /**
     * 注入 JedisCluster 对象
     *
     * @return
     */
    @Bean({"emssJedisCluster"})
    public JedisCluster jedisClusterInit(RedisProperties properties) {
        RedisProperties.Pool pool = properties.getJedis().getPool();
        JedisPoolConfig config = new JedisPoolConfig();
        if (pool != null) {
            config = jedisPoolConfig(pool);
        }

        Set<HostAndPort> hostAndPort = new HashSet<>();
        RedisProperties.Cluster clusterProperties = properties.getCluster();
        List<String> nodes = clusterProperties.getNodes();
        Iterator<String> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String[] split = next.split(":");
            hostAndPort.add(new HostAndPort(split[0], Integer.parseInt(split[1])));
        }
        int maxRedirects = clusterProperties.getMaxRedirects() == 0 ? 5 : clusterProperties.getMaxRedirects();
        Duration timeout = properties.getTimeout();
        int tout = timeout == null ? DEFAULT_TIMEOUT : Math.toIntExact(timeout.toMillis());
        if (StringUtils.isEmpty(properties.getPassword())) {
            return new JedisCluster(hostAndPort, tout, tout, maxRedirects, config);
        } else {
            return new JedisCluster(hostAndPort, tout, tout, maxRedirects, properties.getPassword(), config);
        }
    }

    private JedisPoolConfig jedisPoolConfig(RedisProperties.Pool pool) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        if (pool.getMaxWait() != null) {
            config.setMaxWaitMillis(pool.getMaxWait().toMillis());
        }
        return config;
    }

}
