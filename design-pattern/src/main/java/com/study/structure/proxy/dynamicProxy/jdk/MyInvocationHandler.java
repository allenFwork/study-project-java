package com.study.structure.proxy.dynamicProxy.jdk;



import com.study.structure.proxy.dynamicProxy.Target;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public class MyInvocationHandler implements InvocationHandler {

    private Target target;

    public MyInvocationHandler(Target target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("---------- jdk proxy before --------------");
        Object object = method.invoke(target, args);
        System.out.println("---------- jdk proxy after --------------");

        return object;
    }

}
