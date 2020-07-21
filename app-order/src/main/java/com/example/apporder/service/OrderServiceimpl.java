package com.example.apporder.service;

import com.example.apporder.dao.OrderMapper;
import com.example.apporder.model.Order;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceimpl {

    @Autowired
    OrderMapper orderMapper;

    public Order getOrderId(Integer id){
        Order order = orderMapper.selectByPrimaryKey(id);
        return order != null ? order : new Order();
    }

}
