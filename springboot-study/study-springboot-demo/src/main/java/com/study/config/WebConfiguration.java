package com.study.config;

import com.study.interceptor.CustomizedInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter很重要，
 * 重写他的方法
 */
@Configuration
/**
 * spring 使用 org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration 该类完成自动配置mvc相关配置
 * 使用了 @EnableWebMvc 注解，WebMvcAutoConfiguration就会失效
 */
//@EnableWebMvc // 使用该注解，springboot默认处理资源文件会失效，表示springboot的配置全由自己设置
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private CustomizedInterceptor customizedInterceptor;

    /**
     * 添加拦截器：拦截器在过滤器处理完后处理
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有两层以上的请求，如：/user/save 等，不拦截uri为 /，/index.html，/testServlet等请求
        registry.addInterceptor(customizedInterceptor).addPathPatterns("/**").excludePathPatterns("/index.html", "/");
    }

    /**
     * 添加请求的视图解析器映射配置
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login.html");
    }

}
