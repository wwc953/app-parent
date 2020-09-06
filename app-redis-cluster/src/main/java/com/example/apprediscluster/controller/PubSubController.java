package com.example.apprediscluster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PubSubController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/send1/{message}")
    public String send1(@PathVariable String message) {
        redisTemplate.convertAndSend("TextChannel", message);
        return "send1 success";
    }

    @GetMapping(value = "/send2/{msg}")
    public String send2(@PathVariable String msg) {
        redisTemplate.convertAndSend("kafkaChannel", msg);
        return "send2 success";
    }
}
