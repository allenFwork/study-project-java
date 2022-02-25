package com.study.config;

import com.study.post_processor.CustomizedBeanDefinition2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 测试bean对象的生命周期
 *
 * 执行顺序：
 *      CustomizedBeanDefinition2||我是通过CustomizedBeanDefinition2构造方法 ...
 *      CustomizedRedisBeanPostProcessor||Bean对象的后置处理器||postProcessBeforeInitialization ...
 *      CustomizedBeanDefinition2||我是通过afterPropertiesSet方法 ...
 *      CustomizedBeanDefinition2||我是通过customizedInitMethod方法 ...
 *      CustomizedRedisBeanPostProcessor||Bean对象的后置处理器||postProcessAfterInitialization ...
 */
@Configuration
public class BeanConfig {

    @Bean(initMethod = "customizedInitMethod")
    public CustomizedBeanDefinition2 customizedBeanDefinition2() {
        return new CustomizedBeanDefinition2();
    }

}
