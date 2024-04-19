package com.study.jvm.java_agent.enhancer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER) // 该注解只能添加在参数上
public @interface AgentParam {
    String value();
}
