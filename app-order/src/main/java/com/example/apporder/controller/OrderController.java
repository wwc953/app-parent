package com.example.apporder.controller;

import com.example.apporder.model.Order;
import com.example.apporder.service.OrderServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderServiceimpl orderServiceimpl;

    @GetMapping("/order/{param}")
    public String callOrder(@PathVariable String param) {
        return "我来自：order" + param;
    }

    @GetMapping("/getOrder/{id}")
    public Order getOrder(@PathVariable Integer id){
        return orderServiceimpl.getOrderId(id);
    }

}
