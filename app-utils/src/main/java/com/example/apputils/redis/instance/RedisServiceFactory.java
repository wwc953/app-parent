package com.example.apputils.redis.instance;

import com.example.apputils.redis.api.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/27 15:49
 */
@Configuration
public class RedisServiceFactory {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${redis.implementation.type:spring}")
    private String redisType;

    public RedisServiceFactory() {
    }

    @Bean({"realRedisServiceImpl"})
    public IRedisService initalizeRedisService() throws Exception {
        String clazzName = RedisTypeEnum.getTypeClassByName(this.redisType);
        Assert.hasText(clazzName, "parameter \"redis.type\" is invalid,expected values like 'spring' or 'jedis'");
        Object object = Class.forName(clazzName).newInstance();
        if (this.log.isInfoEnabled()) {
            this.log.info("redis initalze complete.Type is:{}", this.redisType);
        }

        return (IRedisService)object;
    }
}
