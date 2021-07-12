package com.study.entity;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取spring容器中的对象，并进行操作（方法二）
 */
public class SingleBean2 implements BeanFactoryAware, ApplicationContextAware {

    private BeanFactory beanFactory;

    public void sayHello() {
        beanFactory.getBean(Hi.class).sayHi();
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
