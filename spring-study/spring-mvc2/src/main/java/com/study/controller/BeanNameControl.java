package com.study.controller;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BeanNameControl implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 这里不设置返回类型的编码，那么如果报错了（出现异常），会进入异常处理 SimpleExceptionHandle，在那里设置无效
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("BeanNameControl ... ");
        int i = 1 / 0;
    }
}
