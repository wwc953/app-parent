package com.example.appuser.remote;

import com.example.appuser.remote.impl.OrderServiceFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "app-order", fallback = OrderServiceFeignImpl.class)
public interface IOrderServiceFeign {

    @GetMapping("/order/{param}")
    public String callOrder(@PathVariable(value = "param") String param);

    @GetMapping("/getOrder/{id}")
    public String getOrderFromOrder(@PathVariable(value = "id") Integer id);

}
