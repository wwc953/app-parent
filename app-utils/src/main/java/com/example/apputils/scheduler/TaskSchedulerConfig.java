package com.example.apputils.scheduler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Description: 是否启用定时任务配置 application.properties
 * @author: wangwc
 * @date: 2021/1/18 22:10
 */
@Component
@RefreshScope
public class TaskSchedulerConfig {

    @Value("${task.schedulingConfig.config:}")//json串
    private String schedulingConfig;

    @Value("${task.scheduler.switch:true}")
    private boolean taskSwitch;

     public JSONObject getSchedulingConfig(){
         return JSONObject.parseObject(schedulingConfig);
     }

    public boolean isTaskSwitch() {
        return taskSwitch;
    }
}
