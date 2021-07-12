package com.study.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultHandlerInterceptor implements HandlerInterceptor {

    /**
     * 所有的请求都会先进入此方法进行处理
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        System.out.printf("handler object : %s \n", handler.toString() );
        // handler object : public java.lang.String com.study.controller.RestDemoController.index()
        return true;
    }

}
