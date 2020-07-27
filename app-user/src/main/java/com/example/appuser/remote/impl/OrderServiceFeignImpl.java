package com.example.appuser.remote.impl;

import com.example.appuser.remote.IOrderServiceFeign;

public class OrderServiceFeignImpl implements IOrderServiceFeign {
    @Override
    public String callOrder(String param) {
        return "远程调用失败！！！";
    }

    @Override
    public String getOrderFromOrder(Integer id) {
        return "远程调用失败！！！";
    }
}
