package com.example.appuser.controller;

import com.example.appuser.model.User;
import com.example.appuser.remote.IOrderServiceFeign;
import com.example.appuser.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    IOrderServiceFeign orderServiceFeign;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/user/{param}")
    public String callOrder(@PathVariable  String param){
        return orderServiceFeign.callOrder(param);
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUserById(id);
    }

}
