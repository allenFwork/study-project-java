package com.study.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contextFirstParameter = request.getParameter("contextFirstParameter");
        System.out.println(contextFirstParameter);

        /**
         * ServletContext对象
         * 1. javax.servlet.http.HttpServlet 继承了抽象类 javax.servlet.GenericServlet,
         * 2. GenericServlet 中有 ServletContext getServletContext()方法 获取ServletContext对象,
         * 3. ServletContext对象是整个web项目服务共有的,可以取得在web.xml文件中配置的初始化参数
         */
        contextFirstParameter = getServletContext().getInitParameter("contextFirstParameter");
        System.out.println(contextFirstParameter);

        /**
         *
         */
        String servletFirstParameter = this.getInitParameter("servletFirstParameter");
        System.out.println(servletFirstParameter);


        // 重定向
//        response.sendRedirect("/html/hello.html");

        // 转发 : 跳转到另一个servlet去处理
        request.getRequestDispatcher("/goodbye").forward(request, response);
    }

}