package com.study.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoodByeServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletFirstParameter = this.getInitParameter("servletFirstParameter");
        System.out.println(servletFirstParameter);

        response.sendRedirect("/html/goodbye.html");

    }

}
