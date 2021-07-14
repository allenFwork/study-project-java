package com.study.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletContextServlet2 extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse response) throws IOException {

        // 不能写 super.service(); 否则一直返回 405
        // super.service(req, response);
        ServletContext servletContext = getServletContext();
        Object count = servletContext.getAttribute("count");
        if (count == null) {
            servletContext.setAttribute("count", 1);
        } else {
            servletContext.setAttribute("count", Integer.parseInt(count.toString()) + 1);
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print("总计数为 " + servletContext.getAttribute("count"));
        writer.close();
    }

}
