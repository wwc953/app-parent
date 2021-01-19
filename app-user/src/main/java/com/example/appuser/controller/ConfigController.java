package com.example.appuser.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: wangwc
 * @date: 2021/1/19 21:04
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${user.config.url:http://xxx.xx.x.com}")
    private String config;

    @GetMapping("/get")
    public String getConfig(){
        return config;
    }

}
