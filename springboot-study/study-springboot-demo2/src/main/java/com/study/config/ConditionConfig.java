package com.study.config;

import com.study.component.LogComponent1;
import com.study.component.LogComponent2;
import com.study.condition.CustomizedConditional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Conditional 条件注解：将bean对象导入spring容器前会先进行判断
 */
@Configuration
public class ConditionConfig {

//    @Bean
//    public LogComponent1 logComponent1() {
//        return new LogComponent1();
//    }

    /**
     * LogComponent2 是依赖 LogComponent1的，
     * 只有容器中有LogComponent1组件才会加载LogComponent2
     */
    @Bean
    @Conditional(value = CustomizedConditional.class)
    public LogComponent2 logComponent2() {
        return new LogComponent2();
    }

}
