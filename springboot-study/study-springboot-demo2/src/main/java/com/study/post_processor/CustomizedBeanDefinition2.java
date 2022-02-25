package com.study.post_processor;

import org.springframework.beans.factory.InitializingBean;

/**
 * 自定义的Bean对象，将其交由spring容器管理
 * 测试 InitializingBean 接口对应的 afterPropertiesSet 方法执行时机
 */
public class CustomizedBeanDefinition2 implements InitializingBean {

    public CustomizedBeanDefinition2() {
        System.out.println("CustomizedBeanDefinition2||我是通过CustomizedBeanDefinition2构造方法 ... ");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("CustomizedBeanDefinition2||我是通过afterPropertiesSet方法 ... ");
    }

    /**
     * 设置的自定义的初始化方法, 被 init-method 标记, 交由spring容器
     */
    public void customizedInitMethod() {
        System.out.println("CustomizedBeanDefinition2||我是通过customizedInitMethod方法 ... ");
    }

}
