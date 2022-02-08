package com.study.component;

/**
 * 自定义的Bean对象，将其交由spring容器管理
 */
public class CustomizedBeanDefinition  {

    public CustomizedBeanDefinition() {
        System.out.println("CustomizedBeanDefinition||我是通过ImportBeanDefinitionRegistrar注册的bean对象(插件) ... ");
    }

}
