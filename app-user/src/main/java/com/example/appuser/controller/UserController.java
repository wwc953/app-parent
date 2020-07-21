package com.example.appuser.controller;

import com.example.appuser.remote.IOrderServiceFeign;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    IOrderServiceFeign orderServiceFeign;

    @GetMapping("/user/{param}")
    public String callOrder(@PathVariable  String param){
        return orderServiceFeign.callOrder(param);
    }

}
