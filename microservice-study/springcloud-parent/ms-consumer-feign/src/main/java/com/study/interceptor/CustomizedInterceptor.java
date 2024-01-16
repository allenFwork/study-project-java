package com.study.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class CustomizedInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("自定义的Feign的拦截器 ... ");
        // 给所有的请求添加一个token
        requestTemplate.header("token", "123456");
    }

}
