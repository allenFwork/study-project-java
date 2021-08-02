package com.study.mvc;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MvcBeanFactory {

    private ApplicationContext applicationContext;

    public MvcBeanFactory(ApplicationContext applicationContext) {
        Assert.notNull(applicationContext, "argument 'applicationContext' must not be null");
        this.applicationContext = applicationContext;
        loadApiFromSpringBeans();
    }

    // API 接口住的地方 
    private HashMap<String, MvcBean> apiMap = new HashMap<String, MvcBean>();

    private void loadApiFromSpringBeans() {
        apiMap.clear();
        // ioc 所有BEan
        // spring ioc 扫描
        String[] names = applicationContext.getBeanDefinitionNames();
        Class<?> type;
        for (String name : names) {
            type = applicationContext.getType(name);
            for (Method m : type.getDeclaredMethods()) {
                // 通过反射拿到HttpMapping注解
                CustomizedRequestMapping CustomizedRequestMapping = m.getAnnotation(CustomizedRequestMapping.class);
                if (CustomizedRequestMapping != null) {
                    // 封装成一个 MVC bean
                    addApiItem(CustomizedRequestMapping, name, m);
                }
            }
        }
    }

    public MvcBean getMvcBean(String apiName) {
        return apiMap.get(apiName);
    }

    /**
     * 添加 api
     *
     * @param CustomizedRequestMapping api配置
     * @param beanName   beanq在spring context中的名称
     * @param method
     */
    private void addApiItem(CustomizedRequestMapping CustomizedRequestMapping, String beanName, Method method) {
        MvcBean apiRun = new MvcBean();
        apiRun.apiName = CustomizedRequestMapping.value();
        apiRun.targetMethod = method;
        apiRun.targetName = beanName;
        apiRun.context = this.applicationContext;
        apiMap.put(CustomizedRequestMapping.value(), apiRun);
    }


    public boolean containsApi(String apiName, String version) {
        return apiMap.containsKey(apiName + "_" + version);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    // 用于执行对应的API方法，
    public static class MvcBean {

        String apiName;  //url

        String targetName; //controller 名称

        Object target; // controller 实例

        Method targetMethod; // controller方法

        ApplicationContext context;


        public Object run(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            // 懒加载
            if (target == null) {
                // spring ioc 容器里面去服务Bean 比如GoodsServiceImpl
                target = context.getBean(targetName);
            }
            return targetMethod.invoke(target, args);
        }

        public Class<?>[] getParamTypes() {
            return targetMethod.getParameterTypes();
        }

        public String getApiName() {
            return apiName;
        }

        public String getTargetName() {
            return targetName;
        }

        public Object getTarget() {
            return target;
        }

        public Method getTargetMethod() {
            return targetMethod;
        }

    }
}
