package com.example.apputils.scheduler.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ContextLifecycleScheduledTaskRegistrar;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 定时任务配置类
 * @author: wangwc
 * @date: 2021/1/18 22:26
 */
@Configuration
public class ScheduleCore {
    private AtomicInteger threadNum = new AtomicInteger(0);

    @Bean({"scheduledTaskRegistrar"})
    public ContextLifecycleScheduledTaskRegistrar scheduledTaskRegistrar() {
        return new ContextLifecycleScheduledTaskRegistrar();
    }

    @Bean({"threadPoolTaskScheduler"})
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("TaskScheduler-" + threadNum.incrementAndGet());
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        return threadPoolTaskScheduler;
    }

}
