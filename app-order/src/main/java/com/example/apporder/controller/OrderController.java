package com.example.apporder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/order/{param}")
    public String callOrder(@PathVariable String param) {
        return "我来自：order" + param;
    }

}
