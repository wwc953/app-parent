package com.example.apputils.redis.lock.redisson.impl;

import com.example.apputils.redis.lock.api.IDistributeLockService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @Description: Redisson 分布式锁
 * @author: wangwc
 * @date: 2020/11/29 16:31
 */
public class RedissonLockServiceImpl implements IDistributeLockService {
    @Autowired
    Redisson redisson;

    @Override
    public boolean isHeldByCurrentThread(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isHeldByCurrentThread();
    }

    @Override
    public void lock(String lockName, long leaseTime, TimeUnit timeUnit) {
        RLock rLock = redisson.getLock(lockName);
        rLock.lock(leaseTime, timeUnit);
    }

    @Override
    public void lock(String lockName, long leaseTime) {
        RLock rLock = redisson.getLock(lockName);
        rLock.lock(leaseTime, TimeUnit.SECONDS);
    }

    @Override
    public void unLock(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        rLock.unlock();
    }

    @Override
    public boolean isLocked(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isLocked();
    }
}
