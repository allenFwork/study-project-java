package com.study.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 将该拦截器交由spring管理
@Component
public class CustomizedInterceptor implements HandlerInterceptor {

    /**
     * 在处理请求前处理执行
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("this is customizedInterceptor preHandle ... ");
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("this is customizedInterceptor postHandle ... ");
    }

    /**
     * 在处理请求后处理执行
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("this is customizedInterceptor afterCompletion ... ");
    }

}
