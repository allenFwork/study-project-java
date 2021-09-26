package com.study.dubbo.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 通过Spring来启动服务，能够加载dubbo.xml中的配置
 */
public class SpringServerApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-provide.xml");
        ((ClassPathXmlApplicationContext) context).start();
        System.in.read();
    }
}
