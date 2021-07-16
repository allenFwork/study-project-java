package com.study.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用来测试 过滤器功能
 */
public class CommentServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        // 获取评论的内容
        String comment = request.getParameter("comment");
        // 显示评论的内容
        writer.println("<h3>评论内容：" + comment + "</h3>");

        writer.close();
    }

}
