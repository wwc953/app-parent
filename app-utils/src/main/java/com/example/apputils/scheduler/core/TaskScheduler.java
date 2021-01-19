package com.example.apputils.scheduler.core;

import com.alibaba.fastjson.JSONObject;
import com.example.apputils.scheduler.TaskSchedulerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ContextLifecycleScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @Description: 定时任务管理类
 * @author: wangwc
 * @date: 2021/1/18 22:36
 */
@Component
@Configuration
@EnableScheduling
@Slf4j
public class TaskScheduler {
    @Resource
    ContextLifecycleScheduledTaskRegistrar scheduledTaskRegistrar;
    @Resource
    ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Resource
    TaskSchedulerConfig taskSchedulerConfig;
    @Resource
    TaskScheduleTrigger taskScheduleTrigger;

    Map<String, ScheduledFuture<?>> scheduleMap = new HashMap(50);
    Map<String, String> taskScheduleMap = new HashMap(50);

    public TaskScheduler() {
        log.info("TaskScheduler init......");
    }

    public synchronized void configureTasks() {
        if (!taskSchedulerConfig.isTaskSwitch()) {
            log.info("====未配置开启定时任务======");
        } else {
            JSONObject schedulingJson = taskSchedulerConfig.getSchedulingConfig();
            if (scheduledTaskRegistrar != null)
                scheduledTaskRegistrar.destroy();
            scheduledTaskRegistrar.setScheduler(threadPoolTaskScheduler);
            scheduleTask(schedulingJson);
            scheduledTaskRegistrar.afterSingletonsInstantiated();
        }
    }

    public void scheduleTask(JSONObject schedulingJson) {
        Iterator<Map.Entry<String, Object>> iterator = schedulingJson.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> task = iterator.next();
            String taskKey = task.getKey();
            Object cronExpression = task.getValue();
            if (cronExpression == null) {
                log.info("=====> {}任务计划未配置执行时间周期，无须添加!", taskKey);
            } else {
                String cronExpressionStr = task.getValue().toString();
                log.info(Thread.currentThread().getName() + "=====> 加载任务排期,Runnable:{}, cronExpression:{}", taskKey, cronExpressionStr);
                ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(doTask(taskKey, cronExpression),
                        getTrigger(taskKey, cronExpression));
                scheduleMap.put(taskKey, future);
            }
        }
    }

    private Runnable doTask(String taskKey, Object cronExpression) {
        try {
            Class<?> classs = Class.forName(taskKey);
            Constructor<?> constructor = classs.getConstructor(String.class, String.class);
            return (Runnable) constructor.newInstance(taskKey, cronExpression);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Trigger getTrigger(String taskKey, Object cronExpression) {
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {

                if (taskScheduleMap.containsKey(taskKey)) {
                    taskScheduleTrigger.setCronExpression(taskScheduleMap.get(taskKey));
                } else {
                    JSONObject schedulingJSON = taskSchedulerConfig.getSchedulingConfig();
                    if (schedulingJSON.containsKey(taskKey)) {
                        taskScheduleTrigger.setCronExpression(schedulingJSON.getString(taskKey));
                    } else {
                        taskScheduleTrigger.setCronExpression(cronExpression.toString());
                    }
                }
                return taskScheduleTrigger.nextExecutionTime(triggerContext);
            }
        };
    }

    public synchronized boolean addTask(String taskKey, String cronExpression) {
        if (scheduleMap.containsKey(taskKey)) {
            log.info("定时任务{}已存在", taskKey);
            return false;
        }

        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(doTask(taskKey, cronExpression), getTrigger(taskKey, cronExpression));
        log.info("添加定时任务：{}, 任务key:{}", future != null, taskKey);
        scheduleMap.put(taskKey, future);
        return future != null;
    }

    public synchronized boolean modifyTask(String taskKey, String cronExpression) {
        if (!scheduleMap.containsKey(taskKey)) {
            log.info("定时任务{}不存在", taskKey);
            return false;
        }

        stopTask(taskKey);
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(doTask(taskKey, cronExpression), getTrigger(taskKey, cronExpression));
        scheduleMap.put(taskKey, future);
        if (taskScheduleMap.containsKey(taskKey)) {
            taskScheduleMap.replace(taskKey, cronExpression);
        } else {
            taskScheduleMap.put(taskKey, cronExpression);
        }
        log.info("=====> 修改定时任务：{}, 任务Key：{}, cronExpression:{}", future != null, taskKey, cronExpression);
        return future != null;
    }

    public synchronized boolean stopTask(String taskKey) {
        if (!scheduleMap.containsKey(taskKey)) {
            log.info("定时任务{}不存在", taskKey);
            return false;
        }
        ScheduledFuture<?> future = scheduleMap.get(taskKey);
        boolean cancel = future.cancel(true);
        scheduleMap.remove(taskKey);
        taskScheduleMap.remove(taskKey);
        log.info("=====> 停止定时任务：{}, 任务Key：{} ", cancel ? "成功" : "失败", taskKey);
        return cancel;
    }

    public synchronized boolean stopAllTask() {
        if (scheduleMap.isEmpty()) {
            log.info("没有在执行的定时任务");
            return false;
        }
        Iterator<Map.Entry<String, ScheduledFuture<?>>> iterator = scheduleMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ScheduledFuture<?>> futureEntry = iterator.next();
            ScheduledFuture<?> scheduledFuture = futureEntry.getValue();
            boolean result = scheduledFuture.cancel(true);
            scheduleMap.remove(futureEntry.getKey());
            taskScheduleMap.remove(futureEntry.getKey());
            log.info("=====> 停止定时任务, 任务Key：{} ,操作结果：{}", futureEntry.getKey(), result);
        }
        return true;
    }

    public Map<String, ScheduledFuture<?>> getAllTaskRunning() {
        return scheduleMap;
    }


}
