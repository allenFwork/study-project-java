package com.study.spring.bean.lifecycle;


import com.study.spring.bean.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class UserTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        User user = applicationContext.getBean(User.class);
        System.out.println(user);
    }

}