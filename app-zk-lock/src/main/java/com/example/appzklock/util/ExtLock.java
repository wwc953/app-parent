package com.example.appzklock.util;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/7/31 16:37
 */
public interface ExtLock {
    //ExtLock基于zk实现分布式锁
    public void  lock();

    //释放锁
    public void unLock();
}
