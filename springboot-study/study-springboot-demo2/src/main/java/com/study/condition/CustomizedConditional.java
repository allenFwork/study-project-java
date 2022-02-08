package com.study.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CustomizedConditional implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        // 容器中包含 LogComponent1组件 才返回 true
        if(conditionContext.getBeanFactory().containsBean("logComponent1")) {
            return true;
        } else {
            return false;
        }
    }

}
