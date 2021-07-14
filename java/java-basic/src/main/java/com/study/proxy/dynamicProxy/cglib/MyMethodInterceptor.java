package com.study.proxy.dynamicProxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {

    /**
     * @param proxyObject  cglib动态代理生成代理对象:com.study.proxy.TargetObject$$EnhancerByCGLIB$$9337e5e4@90f6bfd
     * @param method       目标对象的方法
     * @param args         方法中的参数
     * @param proxyMethod  代理对象的方法
     * @return
     * @throws Throwable
     */
    public Object intercept(Object proxyObject, Method method, Object[] args, MethodProxy proxyMethod) throws Throwable {
        System.out.println("------------cglib proxy before-----------");
        // 通过代理类调用父类方法
        Object result = proxyMethod.invokeSuper(proxyObject, args);
        System.out.println("------------cglib proxy after-----------");
        return result;
    }

}
