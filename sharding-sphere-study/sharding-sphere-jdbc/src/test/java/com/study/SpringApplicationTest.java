package com.study;

import com.study.service.SpringUseDemo;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationTest {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        SpringUseDemo springUseDemo = context.getBean(SpringUseDemo.class);
        springUseDemo.use();
    }

}
