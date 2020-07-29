package com.example.appeurekaserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/7/24 17:06
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hhhhh";
    }
}
