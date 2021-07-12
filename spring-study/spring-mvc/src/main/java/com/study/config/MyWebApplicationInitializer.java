package com.study.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // 实例化spring容器
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        context.refresh();
        System.out.println("======================================");

        // 注册 DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        ServletRegistration.Dynamic registration = servletContext.addServlet("servletName", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

    }

}