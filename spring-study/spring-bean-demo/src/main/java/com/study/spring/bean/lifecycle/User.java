package com.study.spring.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class User implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

    public User() {
        System.out.println("User的构造方法执行了.........");
    }

    private String name;

    @Value("张三")
    public void setName(String name) {
        this.name = name;
        System.out.println("setName方法执行了.........");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("setBeanName方法执行了.........");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory方法执行了.........");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext方法执行了........");
    }

    @PostConstruct
    public void init() {
        System.out.println("init方法执行了.................");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet方法执行了........");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy方法执行了...............");
    }

}