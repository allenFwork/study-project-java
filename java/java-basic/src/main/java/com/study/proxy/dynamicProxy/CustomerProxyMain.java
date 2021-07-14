package com.study.proxy.dynamicProxy;

import com.study.proxy.dynamicProxy.custom.ProxyUtil2;
import com.study.proxy.dynamicProxy.custom.invocationHandler.CustomerInvocationHandlerImpl;

public class CustomerProxyMain {

    public static void main(String[] args) {

        // 测试 1.0 版本
//        Target proxy = (Target) ProxyUtil.generateProxyObject(new TargetObject());
//        proxy.sayHello();
//        proxy.test(null);
//
//        System.out.println("===================================================================");

//        UserService proxy2 = (UserService) ProxyUtil.generateProxyObject(new UserServiceImpl());
//        proxy2.count(1,2);
//        proxy2.good();
//        proxy2.good("super man");
//        proxy2.goodMorning("bat man");
//        proxy2.goodMorning("good people", 100);

        // 测试 2.0 版本
        Target proxy = (Target) ProxyUtil2.newInstance(Target.class, new CustomerInvocationHandlerImpl(new TargetObject()));
        proxy.sayHello();
        proxy.test(null);

        UserService proxy2 = (UserService) ProxyUtil2.newInstance(UserService.class, new CustomerInvocationHandlerImpl(new UserServiceImpl()));
        System.out.println(proxy2.count(1,2));
        proxy2.good();
        proxy2.good("super man");
        proxy2.goodMorning("bat man");
        proxy2.goodMorning("good people", 100);

    }

}
