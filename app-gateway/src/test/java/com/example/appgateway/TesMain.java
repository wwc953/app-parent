package com.example.appgateway;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2021/1/14 15:57
 */
public class TesMain {
    public static void main(String[] args) {
        //通配符的上界
//        List<? extends People> peopleArrayList = new ArrayList<>();
//        People people = peopleArrayList.get(0);
//        Son son = new Son();
//        peopleArrayList.add(son);//编译报错
        //总结：不能往List<? extends Animal> 添加任意对象，除了null。
        //原因：java保护其类型的一致性

        //通配符的下界
        List<? super People> superList = new ArrayList<>();
        Son son = new Son();
        son.setName("son");
        superList.add(son);

        People people1 = new People();
        people1.setName("people");
        superList.add(people1);

        SSon sSon = new SSon();
        sSon.setName("sson");
        superList.add(sSon);

        for (int i = 0; i < superList.size(); i++) {
            Object object = superList.get(i);
            System.out.println(JSON.toJSONString(object));
        }


        //无界通配符
        List<?> objectArrayList = new ArrayList<>();


    }

}
