package com.study.app;

import com.study.config.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * 为什么实现了 WebApplicationInitializer 接口，Tomcat 就会执行 onStartup方法中的代码？
 * 因为 Tomcat一定会执行 WebApplicationInitializer 的 onStartup方法
 *
 * 想要知道为什么绘制想到 onStartup方法里的代码，可以通过在onStartup方法中设置断点，
 * 然后启动tomcat容器，再通过方法栈的调用，查看是从哪里进来的。
 *
 * 添加 servlet 的方法：
 *  1. web.xml中配置
 *  2. 使用 @WebServlet 注解
 *  3. 实现 WebApplicationInitializer 接口，重写onStartup方法，在方法中注入servlet
 */
//@WebServlet
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        /**
         * 实例化 spring context
         */
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        // 通过 AppConfig配置类，开启了扫描
        context.register(AppConfig.class);
        context.refresh(); // 到这里 spring容器就初始化结束了

        /**
         * 手动添加 spring Servlet:
         * 创建一个 DispatcherServlet实例，并将其注入到spring容器中
         * Create and register the DispatcherServlet
         */
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        // 获取注册器
        ServletRegistration.Dynamic registration = servletContext.addServlet("servletName", dispatcherServlet);
        /**
         * LoadOnStartup 设置为1的原因：
         * 因为需要在tomcat启动时就要立即加载DispatcherServlet类的，
         * 如果不设置为1，就变成了等你请求时tomcat才会加载DispatcherServlet
         */
        registration.setLoadOnStartup(1);
        // 此servlet，即DispatcherServlet,处理所有的请求
//        registration.addMapping("*/");
        // 为了不拦截请求 http://localhost:8080/spring-mvc/index.html, 所以修改了DispatcherServlet会拦截处理的请求
        registration.addMapping("*.do");
    }

}
