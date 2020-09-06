package com.example.apprediscluster.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class RedisMessage implements Serializable {
    public String msgId;
    public long createStamp;
}
