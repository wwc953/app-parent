package com.example.apputils.redis.instance;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/27 15:50
 */
public enum RedisTypeEnum {
    JEDIS("jedis", "com.example.apputils.redis.jedis.impl.JedisRedisServiceImpl"),
    SPRING("spring", "com.example.apputils.redis.spring.impl.SpringRedisServiceImpl");

    private String typeName;
    private String typeClass;

    private RedisTypeEnum(String typeName, String typeClass) {
        this.typeClass = typeClass;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getTypeClass() {
        return this.typeClass;
    }

    public static String getTypeClassByName(String typeName) {
        List<RedisTypeEnum> list = Arrays.asList(values());
        return (String)list.stream().filter((t) -> {
            return typeName.equals(t.getTypeName());
        }).findAny().map(RedisTypeEnum::getTypeClass).orElse(null);
    }
}
