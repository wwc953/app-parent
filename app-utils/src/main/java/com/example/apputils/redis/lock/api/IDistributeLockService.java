package com.example.apputils.redis.lock.api;

import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/29 16:28
 */
public interface IDistributeLockService {
    boolean isHeldByCurrentThread(String lockName);
    void lock(String lockName, long leaseTime, TimeUnit timeUnit);
    void lock(String lockName, long leaseTime);
    void unLock(String lockName);
    boolean isLocked(String lockName);
}
