package com.study.structure.proxy.dynamicProxy.custom.invocationHandler;

import java.lang.reflect.Method;

/**
 * 用来处理动态代理添加的逻辑
 */
public interface CustomerInvocationHandler {

    /**
     * @param targetObjectMethod 目标对象中的方法
     * @param args 方法的参数
     * @return
     */
    public Object invoke(Method targetObjectMethod, Object[] args);
}
