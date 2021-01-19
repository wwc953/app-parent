package com.example.apputils.scheduler.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: 定时器
 * @author: wangwc
 * @date: 2021/1/18 22:04
 */
@Component
@Slf4j
public class TaskScheduleTrigger implements Trigger {
    private String cronExpression;

    public TaskScheduleTrigger() {
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        log.info(Thread.currentThread().getName() + "=====> 初始化定时触发器，时间表达式......" + this.getCronExpression());
        CronTrigger cronTrigger = new CronTrigger(this.getCronExpression());
        return cronTrigger.nextExecutionTime(triggerContext);
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

}
