package com.example.appzuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.concurrent.BlockingDeque;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/7/29 15:39
 */
@Configuration
public class RedisScriptConfig {

    @Bean("slidingWindowRatelimit")
    public RedisScript<Boolean> redisRatelimitLua(){
        DefaultRedisScript<Boolean> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Boolean.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("slidingWindowRatelimit.lua")));
        return defaultRedisScript;
    }

}
