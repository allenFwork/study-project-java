package com.study;

import com.study.config.SpringConfig;
import com.study.config.SpringConfiguration;
import com.study.config.SpringNoAnnotationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.register(SpringConfiguration.class);
        context.register(SpringNoAnnotationContext.class);
        context.refresh();

    }

}
