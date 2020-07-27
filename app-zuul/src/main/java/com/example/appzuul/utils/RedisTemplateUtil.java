package com.example.appzuul.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/7/27 17:10
 */
public class RedisTemplateUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    public Jedis getJedisClient(){
        RedisConnection redisConnection = RedisConnectionUtils.getConnection(redisTemplate.getConnectionFactory());
        Jedis jedis = (Jedis)redisConnection.getNativeConnection();
        return jedis;
    }
}
