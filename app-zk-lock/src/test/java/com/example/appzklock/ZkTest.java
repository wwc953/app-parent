package com.example.appzklock;

import com.example.appzklock.util.ZookeeperDistrbuteLock;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/7/31 16:26
 */
public class ZkTest {
    public static void main(String[] args) {
//        ZkClient zk = new ZkClient("127.0.0.1:2181");
//        for (int i=0;i<10;i++ ) {
//            String asdasd = zk.create("/w2c", "asdasd", CreateMode.EPHEMERAL_SEQUENTIAL);
//            System.out.println("---"+asdasd);
//        }

        ZookeeperDistrbuteLock zookeeperDistrbuteLock = new ZookeeperDistrbuteLock();
        zookeeperDistrbuteLock.lock();


    }
}
