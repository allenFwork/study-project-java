package com.study.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Session的使用
 */
public class SessionServlet2 extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if ("superman".equals(userName) && "123456".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("userName", userName);
            // 编程式设置session的超时时间,10秒
            session.setMaxInactiveInterval(10);
            // 转发
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        } else {
            // 重定向
            response.sendRedirect("/web_servlet/login.jsp");
        }

    }
}
