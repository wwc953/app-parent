package com.example.apputils.scheduler.task;

import com.example.apputils.scheduler.core.TaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: wangwc
 * @date: 2021/1/19 9:42
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TaskScheduler  taskScheduler;

    @GetMapping("/init")
    public String t(){
        taskScheduler.configureTasks();
        return "init";
    }
}
