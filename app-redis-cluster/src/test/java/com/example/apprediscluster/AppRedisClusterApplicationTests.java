package com.example.apprediscluster;

import com.example.apprediscluster.util.ScriptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class AppRedisClusterApplicationTests {

//    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        String script = ScriptUtil.getScript("unlock.lua");
        System.out.println(script);
    }

}
