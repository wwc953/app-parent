package com.example.apprediscluster.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/7/28 15:24
 */
@Configuration
public class JedisClusterConfig {

    @Autowired
    private RedisProperties redisProperties;

    /**
     * 注意：
     * 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
     *
     * @return
     */
    @Bean
    public JedisCluster getJedisCluster() {
        //获取服务器数组
        String[] serverArray = redisProperties.getClusterNodes().split(",");
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();

        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }

        //需要密码连接的创建对象方式
        return new JedisCluster(nodes, redisProperties.getCommandTimeout(), 1000, 1,
                redisProperties.getPassword(), new GenericObjectPoolConfig());
    }

}
