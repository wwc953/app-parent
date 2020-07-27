package com.example.appuser.controller;

import com.example.appuser.model.User;
import com.example.appuser.remote.IOrderServiceFeign;
import com.example.appuser.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    IOrderServiceFeign orderServiceFeign;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/local")
    public String callLocal(){
        log.info("=============== user ===============");
        return "local user";
    }

    @ApiOperation(value = "feign远程服务调用order",notes = "remote call test")
    @GetMapping("/user/{param}")
    public String callOrder(@PathVariable  String param){
        return orderServiceFeign.callOrder(param);
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @GetMapping("/cOrder/{id}")
    public String getOrder(@PathVariable(value = "id") Integer id){
        return orderServiceFeign.getOrderFromOrder(id);
    }

}
