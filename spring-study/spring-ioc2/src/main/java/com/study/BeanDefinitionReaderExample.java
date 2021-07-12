package com.study;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.util.Arrays;

public class BeanDefinitionReaderExample {

    public static void main(String[] args) {

        // 注册中心
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        // 2. 读取xml获取Bead对象，并注册到registry中
        // 读取器
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("spring.xml");
        // 1. 基于resource读取xml配置文件
        reader.loadBeanDefinitions(resource);

        // 获取 hi 的别名
        System.out.println(registry.getAliases("hi"));
        System.out.println(registry.getBeanDefinition("hi"));
        System.out.println(Arrays.toString(registry.getBeanDefinitionNames()));
    }

}
