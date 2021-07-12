package com.study;

import com.study.config.DataSourceConfig;
import com.study.config.SpringConfig;
import com.study.factorybean.MyBeanFactoryPostProcessor;
import com.study.service.UserServiceImpl;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MybatisSpringTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringConfig.class);
        context.register(DataSourceConfig.class);
        context.addBeanFactoryPostProcessor(new MyBeanFactoryPostProcessor());
        context.refresh();
        context.getBean(UserServiceImpl.class).query();
    }

}
