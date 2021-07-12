package com.study.spring.tx;

import com.study.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CustomizedTransaction {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-tx.xml");

        final UserService userService = context.getBean(UserService.class);

        UserService proxyUserService =
                (UserService) Proxy.newProxyInstance(CustomizedTransaction.class.getClassLoader(),
                        new Class[]{UserService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try {
                    // spring护在这里将事务设置为手动提交
                    System.out.println("开启事务：" + method.getName());
                    return method.invoke(userService, args);
                } finally {
                    System.out.println("关闭事务：" + method.getName());
                }
            }
        });

        // 调用方法，进行测试
        proxyUserService.createUser("batman");
    }

}
