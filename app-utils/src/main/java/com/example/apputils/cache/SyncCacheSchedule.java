package com.example.apputils.cache;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.security.ValidationState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 刷新缓存定时任务
 * @author: wangwc
 * @date: 2020/11/30 11:14
 */
@Configuration
@EnableScheduling
@ConditionalOnExpression("${frame.cmccache.use:true}")
@Slf4j
public class SyncCacheSchedule {

    @Value("${frame.cmccache.use.type:aa,bb,cc,dd}")
    private String type;

    @Scheduled(initialDelay = 1000L, fixedDelay = 90000L)
    private void task() {
        log.info("=== 刷新本地缓存定时任务开始: ===");
        List<String> typeList = Arrays.asList(type.split(","));
        log.info("需要刷新的key:{}", JSON.toJSONString(typeList));
        CommonParamManager.refresh(typeList);
    }

}
