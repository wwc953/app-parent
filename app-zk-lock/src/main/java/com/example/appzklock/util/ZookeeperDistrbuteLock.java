package com.example.appzklock.util;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/7/31 16:37
 */
public class ZookeeperDistrbuteLock implements ExtLock {

    private static final String CONNECTION="127.0.0.1:2181";
    private ZkClient zkClient = new ZkClient(CONNECTION);
    private String lockPath="/distribute_lock";
    private CountDownLatch countDownLatch;

    //获取锁
    @Override
    public void lock() {
        // 如果节点创建成果，直接执行业务逻辑，如果节点创建失败，进行等待
        if (tryLock()) {
            System.out.println("#####成功获取锁######");
        }else {
            //进行等待
            waitLock();
        }
    }

    //释放锁
    @Override
    public void unLock() {
        //执行完毕 直接连接
        if (zkClient != null) {
            zkClient.close();
            System.out.println("######释放锁完毕######");
        }
    }

    public boolean tryLock() {
        try {
            zkClient.createEphemeral(lockPath);
            return true;
        } catch (Exception e) {
            // 如果失败 直接catch
            return false;
        }
    }

    public void waitLock() {
        IZkDataListener iZkDataListener = new IZkDataListener() {
            // 节点被删除
            public void handleDataDeleted(String arg0) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown(); // 计数器为0的情况，await 后面的继续执行
                }
            }
            // 节点被修改
            public void handleDataChange(String arg0, Object arg1) throws Exception {
                System.out.println("########节点被修改#######");
            }
        };

        // 监听事件通知
        zkClient.subscribeDataChanges(lockPath, iZkDataListener);
        // 控制程序的等待
        if (zkClient.exists(lockPath)) {  //如果检查出已经被创建了就等待
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.wait(); //当为0时候，后面的继续执行
            } catch (Exception e) {
            }
        }
        //后面代码继续执行
        //删除该事件监听
        zkClient.unsubscribeDataChanges(lockPath, iZkDataListener);
    }

}
