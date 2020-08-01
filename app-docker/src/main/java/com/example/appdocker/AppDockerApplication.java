package com.example.appdocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@RequestMapping
public class AppDockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDockerApplication.class, args);
    }

    @RequestMapping("/aa/{code}")
    @ResponseBody
    public String hello(@PathVariable String code){
        return "hello:"+code;
    }

}
