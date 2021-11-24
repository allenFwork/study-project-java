package com.study.test;

import com.study.config.KeyValueConfig;
import com.study.config.SpringConfig;
import com.study.service.OrderTabServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAopTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.refresh();
        OrderTabServiceImpl orderTabService = context.getBean(OrderTabServiceImpl.class);
        orderTabService.query("A");
        orderTabService.query("B");

    }

}