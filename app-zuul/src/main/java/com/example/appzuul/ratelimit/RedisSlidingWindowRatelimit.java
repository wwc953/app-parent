package com.example.appzuul.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Description: Redis滑动窗口限流
 * @author: wangwc
 * @date: 2020/7/24 9:33
 */
@Slf4j
@Component
public class RedisSlidingWindowRatelimit {

    private static final String PRE_KEY = "ratelimit:";

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private boolean isprint = false;

    /**
     * @param key         key
     * @param millisecond 窗口(单位时间)
     * @param limitCount  次数
     */
    public boolean redisRatelimit(String key, Long millisecond, Integer limitCount) {
        //TODO 分布式锁

        key = PRE_KEY + key;
        Long listSize = stringRedisTemplate.opsForList().size(key);
        log.info("-----当前{},list长度：{}", key, listSize);

        printList(key);

        if (listSize < limitCount) {
            log.info("list 未满");
            stringRedisTemplate.opsForList().leftPush(key, System.currentTimeMillis() + "");
            return true;
        } else {
            //获取队列租后一个值 -1: 代表最后一个值
            Long endListTime = Long.parseLong(stringRedisTemplate.opsForList().index(key, -1));
            long currentTime = System.currentTimeMillis();
            log.info("currentTime：{}，endListTime：{},差值：{}", currentTime, endListTime, currentTime - endListTime);
            if (currentTime - endListTime >= millisecond) {
                stringRedisTemplate.opsForList().leftPush(key, currentTime + "");
                stringRedisTemplate.opsForList().trim(key, 0, limitCount - 1);
//                stringRedisTemplate.opsForList().rightPop(key);
                printList(key);
                return true;
            }
        }
        printList(key);
        return false;
    }

    private void printList(String key) {
        if (!isprint) {
            return;
        }
        List<String> range = stringRedisTemplate.opsForList().range(key, 0, -1);
        log.info("{}", range);
    }

    @Autowired
    RedisScript<String> redisRatelimitLua;

//    调用slidingWindowRatelimit.lua 实现滑动窗口限流

    /**
     * @param key         队列 Key
     * @param millisecond 窗口时间(ms) 默认10000ms
     * @param limitCount  次数 默认20
     * @param currentTime 当前时间(ms) 默认当前时间
     * @return 0 队列已满(限流); 1 队列未满(未限流)
     */
    public boolean redisRatelimit4Lua(String key, Long millisecond, Integer limitCount, Long currentTime) {
//        Jedis jedis = (Jedis) stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
//        jedis.eval();
        if(millisecond == null || millisecond <= 0){
            millisecond = 10000L;
        }
        if(limitCount == null || limitCount <= 0){
            limitCount = 100;
        }
        if (currentTime == null || currentTime <= 0) {
            currentTime = System.currentTimeMillis();
        }

        String result = stringRedisTemplate.execute(redisRatelimitLua, Collections.singletonList(key),
                millisecond + "", limitCount + "", currentTime + "");

        return "true".equals(result) ? true : false;
    }
}
