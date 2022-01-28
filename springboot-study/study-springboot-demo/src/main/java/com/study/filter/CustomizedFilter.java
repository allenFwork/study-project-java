package com.study.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * 自定义过滤器
 */
public class CustomizedFilter implements Filter {

    /**
     * 启动spring容器，初始化过滤器时执行
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("customizedFilter init ... ");
    }

    /**
     * 过滤请求, 在处理请求之前执行
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("this is customizedFilter doFilter ... ");
        // 接着向下过滤执行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("customizedFilter destroy ... ");
    }

}
