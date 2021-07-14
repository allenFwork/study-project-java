package com.study.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Session的使用
 */
public class SessionServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        // 获取 session 对象
        HttpSession session = request.getSession();
        // 输出 sessionId
        System.out.println(session.getId());
        Integer count = (Integer) session.getAttribute("count");
        if (count != null) {
            count++;
        } else {
            count = 1;
        }
        // 在 session 中绑定计数器
        session.setAttribute("count", count);
        // 输出提示信息
        writer.print("这是第" + count + "次访问");
        writer.close();
    }

}
