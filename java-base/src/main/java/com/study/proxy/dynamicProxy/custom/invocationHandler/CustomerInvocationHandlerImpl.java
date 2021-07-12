package com.study.proxy.dynamicProxy.custom.invocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用来处理动态代理添加的逻辑的具体实现
 */
public class CustomerInvocationHandlerImpl implements CustomerInvocationHandler {

    // 目标对象
    private Object targetObject;

    public CustomerInvocationHandlerImpl(Object targetObject) {
        this.targetObject = targetObject;
    }

    /**
     *
     * @param targetObjectMethod 目标对象中的方法
     * @param args 方法的参数
     * @return
     */
    @Override
    public Object invoke(Method targetObjectMethod, Object[] args) {
        Object object = null;
        try {
            /*----------------代理类中添加的逻辑-----------------*/
            System.out.println("----------------CustomerInvocationHandlerImpl before ... -------------------");
            object = targetObjectMethod.invoke(targetObject, args);
            System.out.println("----------------CustomerInvocationHandlerImpl after  ... -------------------");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }
}