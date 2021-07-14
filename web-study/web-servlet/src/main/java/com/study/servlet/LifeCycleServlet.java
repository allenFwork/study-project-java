package com.study.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet的生命周期
 */
public class LifeCycleServlet extends HttpServlet {

    // 1.实例化阶段
    public LifeCycleServlet() {
        System.out.println("1. Constructor is running ... ");
    }

    // 初始化阶段
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("2. Init is running ... ");
    }

    // 就绪阶段
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        System.out.println("3. 就绪阶段 ... ");
        // 获取 ServletConfig对象
        ServletConfig servletConfig = getServletConfig();
        String supermanPower = servletConfig.getInitParameter("superman");
        String batmanPower   = servletConfig.getInitParameter("batman");
        System.out.println("superman" + supermanPower);
        System.out.println("batman" + batmanPower);
    }

    // 销毁阶段
    @Override
    public void destroy() {
        super.destroy();
        System.out.println("4. Destroy is running ... ");
    }

}
