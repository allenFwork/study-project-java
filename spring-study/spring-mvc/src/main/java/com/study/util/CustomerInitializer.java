package com.study.util;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

/**
 * 实现了 javax.servlet.ServletContainerInitializer 接口，重写onStartup方法，
 * 如果tomcat启动时，加载到了 CustomerInitializer 这个类，那么一定会执行这个重写的onStartup方法。
 */
@HandlesTypes(HandlesTypesDemo.class)
public class CustomerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("------------------ CustomerInitializer onStartup() ... -----------------");
        System.out.println(set);
    }

}
