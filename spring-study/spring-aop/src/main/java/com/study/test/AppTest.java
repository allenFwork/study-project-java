package com.study.test;

import com.study.configuration.SpringConfiguration;
import com.study.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class AppTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfiguration.class);
        context.refresh();
        UserService userService = context.getBean(UserService.class);
        userService.hello();
        userService.hello2("name");

    }

}
