package com.example.apprediscluster.controller;

import com.example.apprediscluster.util.RedisCluterLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class SkillController {

    private Integer total = 1000;

    @Autowired
    private RedisCluterLock lock;

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String KEY = "wwc";

    public AtomicInteger err=new AtomicInteger(0);

    @GetMapping("/skill")
    public void skill() {
//        String value = ;
        String value = "3333";
//        threadLocal.set(System.currentTimeMillis() + "");

        boolean result = lock.trylock(KEY, value);
        try {
            if (result && total > 0) {
                total--;
                System.out.println("剩余：" + total+",线程："+Thread.currentThread().getName());
            }
//            else{
//                System.out.println(err.incrementAndGet());
//            }
        } finally {
            lock.unlock(KEY, value);
        }

    }


}
