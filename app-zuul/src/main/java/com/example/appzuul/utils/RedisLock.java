package com.example.appzuul.utils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

/**
 * @Description: Redis分布式锁（单节点）
 * @author: wangwc
 * @date: 2020/7/27 15:47
 */
@Slf4j
public class RedisLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String SET_IF_NOT_EXIST = "NX";//setnx
    /**
     * 时间单位：ms
     */
    private static final String SET_WITH_EXPIRE_TIME = "PX"; //ms
    /**
     * 时间单位：s
     */
    private static final String SET_WITH_EXPIRE_TIME_SEC = "EX"; //s

    /**
     * redis 客户端
     */
    private Jedis jedis;

    /**
     * 分布式锁的键值
     */
    private String lockKey;

    /**
     * 锁的超时时间 10s
     */
    int expireTime_ms = 10 * 1000;
    int expireTime_s = 10;

    /**
     * 锁等待，防止线程饥饿 ms
     */
    int acquireTimeout = 1 * 1000;

    /**
     * 获取指定键值的锁
     *
     * @param jedis   jedis Redis客户端
     * @param lockKey 锁的键值
     */
    public RedisLock(Jedis jedis, String lockKey) {
        this.jedis = jedis;
        this.lockKey = lockKey;
    }

    /**
     * 获取指定键值的锁,同时设置获取锁超时时间
     *
     * @param jedis          jedis Redis客户端
     * @param lockKey        锁的键值
     * @param acquireTimeout 获取锁超时时间
     */
    public RedisLock(Jedis jedis, String lockKey, int acquireTimeout) {
        this(jedis, lockKey);
        this.acquireTimeout = acquireTimeout;
    }

    /**
     * 获取指定键值的锁,同时设置获取锁超时时间和锁过期时间
     *
     * @param jedis          jedis Redis客户端
     * @param lockKey        锁的键值
     * @param acquireTimeout 获取锁超时时间ms
     * @param expireTime_ms     锁失效时间ms
     */
    public RedisLock(Jedis jedis, String lockKey, int acquireTimeout, int expireTime_ms) {
        this(jedis, lockKey, acquireTimeout);
        this.expireTime_ms = expireTime_ms;
    }

    public String lock() {
        try {
            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            // 随机生成一个value
            String requireToken = UUID.randomUUID().toString();
            while (System.currentTimeMillis() < end) {
                String result = jedis.set(lockKey, requireToken, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime_ms);
                if (LOCK_SUCCESS.equals(result)) {
                    return requireToken;
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

        return null;
    }

    public boolean unlock(String identify) {
        if (identify == null) {
            return false;
        }

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = new Object();
        try {
            result = jedis.eval(script, Collections.singletonList(lockKey),
                    Collections.singletonList(identify));
            if (RELEASE_SUCCESS.equals(result)) {
                log.info("release lock success, requestToken:{}", identify);
                return true;
            }
        } catch (Exception e) {
            log.error("release lock due to error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        log.info("release lock failed, requestToken:{}, result:{}", identify, result);
        return false;
    }

    /**
     * 实现加锁的错误姿势1
     *
     * @param jedis
     * @param lockKey
     * @param requestId
     * @param expireTime_s
     */
    @Deprecated
    public static void wrongGetLock1(Jedis jedis, String lockKey, String requestId, int expireTime_s) {
        Long result = jedis.setnx(lockKey, requestId);
        if (result == 1) {
            // 若在这里程序突然崩溃，则无法设置过期时间，将发生死锁
            jedis.expire(lockKey, expireTime_s);
        }
    }

    /**
     * 解锁错误姿势1
     *
     * @param jedis
     * @param lockKey
     */
    @Deprecated
    public static void wrongReleaseLock1(Jedis jedis, String lockKey) {
        jedis.del(lockKey);
    }

    /**
     * 解锁错误姿势2
     *
     * @param jedis
     * @param lockKey
     * @param requestId
     */
    @Deprecated
    public static void wrongReleaseLock2(Jedis jedis, String lockKey, String requestId) {

        // 判断加锁与解锁是不是同一个客户端
        if (requestId.equals(jedis.get(lockKey))) {
            // 若在此时，这把锁突然不是这个客户端的，则会误解锁
            jedis.del(lockKey);
        }
    }

}
