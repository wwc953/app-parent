package com.example.apprediscluster;

import com.example.apprediscluster.util.RedisCluterLock;
import com.example.apprediscluster.util.ScriptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppRedisClusterApplicationTests {
    @Autowired
    RedisCluterLock redisCluterLock;


    @Test
    void contextLoads() {
        boolean trylock = redisCluterLock.trylock("wwc", "9999");
        System.out.println(trylock);
    }

//    public static void main(String[] args) {
//        String script = ScriptUtil.getScript("unlock.lua");
//        System.out.println(script);
//    }

}
