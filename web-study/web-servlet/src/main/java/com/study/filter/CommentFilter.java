package com.study.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CommentFilter implements Filter {

    /**
     * 容器启动之后，创建过滤器实例
     * 然后调用init方法，只会嗲用一次
     * 容器会将已经创建好的 FilterConfig 对象作为参数传入
     * 可以从该参数中获取初始化的配置信息
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化，执行 CommentFilter 的 init 方法 ... ");
        // 获取初始化参数
        String name = filterConfig.getInitParameter("name");
        System.out.println(name);
    }

    // 用于处理请求的主要方法
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        System.out.println("过滤器的处理逻辑，执行 CommentFilter 的 doFilter 方法(开始) ... ");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        /*
         *  最终执行，还是会回归到这个 doFilter方法，从这个方法返回给客户端（浏览器），
         *  所以此处设置的response.setContent有效
         *  在向后处理的 Servlet 中，给 response.setContent 无效
         */
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String comment = request.getParameter("comment");
        if (comment.indexOf("fuck") != -1) {
            writer.print("<h3>评论内容已关闭</h3>");
        } else {
            // 没有敏感词，向后处理，叫有其他过滤器 或 Servlet 处理（不执行下面的语句，则会直接返回）
            filterChain.doFilter(request, response);
        }

        System.out.println("过滤器的处理逻辑，执行 CommentFilter 的 doFilter 方法(结束) ... ");

    }

    // 容器删除过滤器实例之前嗲用，只执行一次
    @Override
    public void destroy() {
        System.out.println("过滤器被销毁，执行 CommentFilter 的 destroy 方法 .... ");
    }

}
