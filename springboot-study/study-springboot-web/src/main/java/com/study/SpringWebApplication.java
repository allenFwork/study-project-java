package com.study;

import com.study.interceptor.DefaultHandlerInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SpringWebApplication extends WebMvcConfigurerAdapter implements ErrorPageRegistrar {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebApplication.class, args);
    }

    /**
     * 继承了 WebMvcConfigurerAdapter类，重写添加拦截器的方法
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultHandlerInterceptor());
    }

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/404.html"));
    }
}
