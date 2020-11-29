package com.example.apputils.redis.jedis.impl;

import com.example.apputils.redis.api.IRedisService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/29 14:57
 */
@Configuration
@ConditionalOnExpression("('${redis.implementation.type:spring}'.equals('jedis')) and !T(org.springframework.util.StringUtils).isEmpty('${spring.redis.host:}') and T(org.springframework.util.StringUtils).isEmpty('${spring.redis.cluster.nodes:}')")
@EnableConfigurationProperties({RedisProperties.class})
public class JedisPoolConfiguration {
    private static final int DEFAULT_TIMEOUT = 6000;

    public JedisPoolConfiguration() {

    }

    @Bean({"emssJedisPool"})
    public JedisPool jedisPoolInit(RedisProperties properties) {
        RedisProperties.Pool pool = properties.getJedis().getPool();
        JedisPoolConfig config = new JedisPoolConfig();
        if (pool != null) {
            config = jedisPoolConfig(pool);
        }
        JedisPool jedisPool = new JedisPool(config, properties.getHost(),
                properties.getPort(),
                properties.getTimeout() == null ? DEFAULT_TIMEOUT : Math.toIntExact(properties.getTimeout().toMillis()),
                properties.getPassword());
        return jedisPool;
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

    @Bean
    public IRedisService jedisRedisServiceImpl() {
        return new JedisRedisServiceImpl();
    }

}
