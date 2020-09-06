package com.example.apprediscluster.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserMessage extends RedisMessage {
    private String userId;
    private String username;
    private String password;
}