package com.example.appserialization.controller;

import com.example.appserialization.kryo2.KryoSerializer;
import com.example.appserialization.po.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/1 14:39
 */
@RestController
@RequestMapping("/put")
public class PutRedisController {

    @GetMapping("/t/{type}/{size}")
    public String put(@PathVariable String type, @PathVariable int size) {
        List<Person> args = new ArrayList<Person>();
        for (int i = 0; i < size; i++) {
            Person s = new Person();
            s.setAge(30);
            s.setBeginTime(new Date());
            s.setBigDecimal(new BigDecimal(i * 10 + Math.random()));
            s.setName("我是是" + i);
            s.setId(Long.valueOf("300000" + i));
            args.add(s);
        }

        switch (type){
            case "kryo":
                long begin = System.currentTimeMillis();
                byte[] bytes = KryoSerializer.serialize(args);
                int bb = bytes.length / 1024;
                int mm = bb / 1024;
                System.out.println("kryo序列化时间：" + (System.currentTimeMillis() - begin) + "ms,大小：" + bb + "KB," + mm + "MB");

                long begin2 = System.currentTimeMillis();
                List<Person> deserialize = (List<Person>) KryoSerializer.deserialize(bytes);
                System.out.println("kryo反序列化时间：" + (System.currentTimeMillis() - begin2) + "ms,反序列化：" + deserialize.size());

                break;
            case "pf":
                break;
            case "json":
                break;
            default:
                System.out.println("=====空====");
                break;
        }
        return "success";

    }

}
