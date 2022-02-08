package com.study.component;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 向容器中添加组件(Bean对象)方法二：
 * 实现 org.springframework.context.annotation.ImportBeanDefinitionRegistrar接口，
 * 重写registerBeanDefinitions方法，在该方法中注册bean对象到容器中(字节能够管理对象/组件的创建过程)
 */
public class CustomizedBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 创建Bean对象,需要指定的bean的类作为参数
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(CustomizedBeanDefinition.class);
        // 注册到spring容器中，交由spring的工厂管理
        beanDefinitionRegistry.registerBeanDefinition("customizedBeanName", rootBeanDefinition);
    }

}
