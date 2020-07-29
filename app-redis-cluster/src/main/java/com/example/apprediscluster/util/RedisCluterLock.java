package com.example.apprediscluster.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * @Description: Redis Cluter模式集群
 * @author: wangwc
 * @date: 2020/7/28 15:09
 */
@Slf4j
@Component
public class RedisCluterLock {

    @Autowired
    private JedisCluster jedisCluster;

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 分布式锁的键前缀
     */
//    private String preKey = "REDIS_LOCK_";
    private String preKey = "";


    /**
     * 锁的超时时间 10s
     */
    int expireTime = 10 * 1000;

    /**
     * 锁等待，防止线程饥饿
     */
    int acquireTimeout = 1 * 1000;

    public boolean trylock(String key, String value) {
        try {
            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;

            while (System.currentTimeMillis() < end) {
                String result = jedisCluster.set(preKey + key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            log.error("acquire lock due to error", e);
        }
        return false;
    }

    public String lockByLua(String key, long expireTime) {
        String lockLua = ScriptUtil.getScript("lock.lua");
        Object result = jedisCluster.eval(lockLua, Collections.singletonList(key),
                Collections.singletonList(expireTime + ""));
        if (!"0".equals(result)) {
            return String.valueOf(result);
        }
        return "0";
    }

    public boolean unlock(String key, String value) {
        if (value == null) {
            return false;
        }
        key = preKey + key;

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = null;
        try {
            result = jedisCluster.eval(script, Collections.singletonList(key),
                    Collections.singletonList(value));
            if (RELEASE_SUCCESS.equals(result)) {
//                log.info("release lock success, value:{}", value);
                return true;
            }
        } catch (Exception e) {
            log.error("release lock due to error", e);
        }
//        log.info("release lock failed, value:{}, result:{}", value, result);
        return false;
    }


//    @Autowired
//    private RedisTemplate redisTemplate;

//    @Autowired
//    JedisClusterConnection clusterConnection;

//    public JedisCluster getJedisCluster(){
//        RedisClusterConnection clusterConnection = redisTemplate.getConnectionFactory().getClusterConnection();
//        JedisCluster jedisCluster = (JedisCluster)clusterConnection.getNativeConnection();
//        return jedisCluster;
//    }

}
