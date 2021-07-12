package com.study.factorybean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * 自己注册 自定义的BeanDefinition
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("----------------- MyBeanFactoryPostProcessor postProcessBeanDefinitionRegistry() -----------------");
    }

    /**
     * 工厂后置处理
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("----------------- MyBeanFactoryPostProcessor postProcessBeanFactory() -----------------");
    }
}
