package com.study.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常处理
 */
public class ExceptionServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        try {
            System.out.println("异常处理");
            String str = null;
            str.equals("abc");
        } catch (Exception e) {

            // 方法一：编程式的异常处理
//            request.setAttribute("err_msg", "系统出错，请重试");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);

            // 方法二：将异常抛给容器，在 web.xml 中添加声明，变成声明式的异常处理
            throw new ServletException(e);

        }


    }
}
