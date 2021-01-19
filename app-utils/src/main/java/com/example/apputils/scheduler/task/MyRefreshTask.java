package com.example.apputils.scheduler.task;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2021/1/19 9:29
 */
public class MyRefreshTask implements Runnable {
    private String taskKey;
    private String cronExpression;

    public MyRefreshTask() {
    }

    public MyRefreshTask(String taskKey, String cronExpression) {
        this.taskKey = taskKey;
        this.cronExpression = cronExpression;
    }

    @Override
    public void run() {
        System.out.println("hhaha");
    }
}
