package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class UploadConfig {

//    /**
//     * 使用 Apache Commons FileUpload 技术实现文件上传，
//     * 可以通过向spring容器注入CommonsMultipartResolver的实例对象，
//     * 但是这个实例对象的对应的BeanDefinition的BeanName必须是 multipartResolver
//     */
//    @Bean("multipartResolver")
//    public CommonsMultipartResolver createCommonsMultipartResolver() {
//        return new CommonsMultipartResolver();
//    }

    @Bean("multipartResolver")
    public StandardServletMultipartResolver createStandardServletMultipartResolver() {
        return new StandardServletMultipartResolver();
    }

}
