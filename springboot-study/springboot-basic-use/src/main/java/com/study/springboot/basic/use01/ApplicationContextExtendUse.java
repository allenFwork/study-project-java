package com.study.springboot.basic.use01;

import com.study.springboot.basic.use01.event.UserRegisterServiceImpl;
import com.study.springboot.basic.use01.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

/**
 * BeanFactory 与 ApplicationContext 的区别
 */
@SpringBootApplication
public class ApplicationContextExtendUse {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContextExtendUse.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {

        ConfigurableApplicationContext context = SpringApplication.run(ApplicationContextExtendUse.class, args);
        /*
         * 1. 到底什么是 BeanFactory
         *      - 它是 ApplicationContext 的父接口
         *      - 它才是 Spring 的核心容器, 主要的 ApplicationContext 实现都【组合】了它的功能
         */
        System.out.println(context);


        /*
         * 2. BeanFactory 能干点啥
         *      - 表面上只有 getBean
         *      - 实际上控制反转、基本的依赖注入、直至 Bean 的生命周期的各种功能, 都由它的实现类提供
         */
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        map.entrySet()
                .stream()
                .filter(e -> e.getKey().startsWith("component"))
                .forEach(e -> {
                    System.out.println(e.getKey() + "=" + e.getValue());
                });

        /*
         * 3. ApplicationContext 比 BeanFactory 多点啥（如下4个）
         *      - 国际化
         *      - 资源路径匹配获取资源对象
         *      - 整合 Environment 环境
         *      - 发布事件
         */
        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
        System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

        // classpath:xxx  表示在当前项目的类路径下面寻找资源;
        // classpath*:xxx 表示在 当前项目 和 它依赖的项目（打成包的项目） 的类路径下
        Resource[] resources = context.getResources("classpath:META-INF/spring.factories");
//        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        System.out.println(context.getEnvironment().getProperty("java_home"));
        System.out.println(context.getEnvironment().getProperty("server.port"));

        // 通过spring容器发布事件，触发它监听的类
//        context.publishEvent(new UserRegisteredEvent(context));
        // 通过实际逻辑发布事件
        context.getBean(UserRegisterServiceImpl.class).register();

        /*
         * 4. 学到了什么
         *      a. BeanFactory 与 ApplicationContext 并不仅仅是简单接口继承的关系, ApplicationContext 组合并扩展了 BeanFactory 的功能
         *      b. 又新学一种代码之间解耦途径
         * 练习：完成用户注册与发送短信之间的解耦, 用事件方式、和 AOP 方式分别实现
         */

    }

}
