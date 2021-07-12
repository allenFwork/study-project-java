package com.study.util;

import com.study.dao.IndexMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

//    IndexMapper indexMapper;
//
//    public MyInvocationHandler(IndexMapper indexMapper){
//        this.indexMapper = indexMapper;
//    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }

}
