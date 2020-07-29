package com.example.appzuul;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;
import java.util.UUID;

@SpringBootTest
class AppZuulApplicationTests {

    @Test
    void contextLoads() {
        JedisCluster jedisCluster = new JedisCluster(new HostAndPort("127.0.0.1", 6789));
        String set = jedisCluster.set("_lock_", "sada", "nx", "px", 10000L);

        Object result = jedisCluster.eval("", Collections.singletonList(null),
                Collections.singletonList(null));
    }

}
