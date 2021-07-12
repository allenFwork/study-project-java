package com.study.imports;

import com.study.dao.IndexMapper;
import com.study.util.MyInvocationHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Proxy;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        // 扫描获取所有的mapper接口
        IndexMapper proxyObject = (IndexMapper)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{IndexMapper.class}, new MyInvocationHandler());
        proxyObject.query();
        Class clazz = proxyObject.getClass();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        registry.registerBeanDefinition("indexMapper", beanDefinition);


    }

}
