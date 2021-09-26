package com.study.dubbo.client;

import com.study.dubbo.server.Bean.UserVo;
import com.study.dubbo.server.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


/**
 * spring启动客户端，加载dubbo.xml(spring-consumer.xml)中的配置
 */
public class SpringClientApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-consumer.xml");
        UserService userService = context.getBean(UserService.class);
        while (!read().equals("exit")) {
            UserVo u = userService.getUser(1111);
            System.out.println(u);
        }
    }

    private static String read() throws IOException {
        byte[] b = new byte[1024];
        int size = System.in.read(b);
        return new String(b, 0, size).trim();
    }

}

