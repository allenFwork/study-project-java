package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SpringBootValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootValidationApplication.class, args);
    }

//    /**
//     * 继承了 WebMvcConfigurerAdapter类，重写添加拦截器的方法
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new UserControllerInterceptor());
//    }

}
