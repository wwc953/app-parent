package com.example.apputils.redis.jedis.impl;

import com.example.apputils.redis.api.IRedisService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

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
     * @return
     */
    @Bean
    public JedisCluster jedisClusterInit(){
        return null;
    }

}
