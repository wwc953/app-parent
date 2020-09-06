package com.example.apprediscluster.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsMessage extends RedisMessage {
    private String goodsType;
    private String number;
}