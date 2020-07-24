package com.example.appzuul.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

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

    private boolean isprint = true;

    /**
     * @param key key
     * @param millisecond 毫秒数
     * @param limitCount  限流次数
     */
    public boolean redisRatelimit(String key, Long millisecond, Integer limitCount) {
        //TODO 是否需要分布式锁?

        key = PRE_KEY + key;
        Long listSize = stringRedisTemplate.opsForList().size(key);
        log.info("-----当前{},list长度：{}", key, listSize);

        printList(key);

        if (listSize < limitCount) {
            log.info("list 未满");
            stringRedisTemplate.opsForList().leftPush(key, System.currentTimeMillis() + "");
            return true;
        } else {
            Long lastTime = Long.parseLong(stringRedisTemplate.opsForList().index(key, -1));
            long nowTime = System.currentTimeMillis();
            log.info("当前时间：{}，最后一次时间：{},差值：{}", nowTime, lastTime, nowTime - lastTime);
            if (nowTime - lastTime >= millisecond) {
                stringRedisTemplate.opsForList().leftPush(key, nowTime + "");
                stringRedisTemplate.opsForList().trim(key, 0, limitCount-1);
                printList(key);
                return true;
            }
        }
        printList(key);
        return false;
    }

    private void printList(String key){
        if(!isprint){
            return;
        }
        List<String> range = stringRedisTemplate.opsForList().range(key, 0, -1);
        System.out.println(range);
    }
}
