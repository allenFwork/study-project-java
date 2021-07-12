package com.study.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 配置一个切面
 */
@Component("aspect")
@Aspect
public class CustomerAspect {

    /**
     * 定义一个切点
     */
    @Pointcut("execution(* com.study.service.*.*(..))")
    public void pointCutExecution(){
        System.out.println("pointcut execution ...");
    }

    @Before(value="pointCutExecution()")
    public void before(){
        System.out.println("advice: before ..");
    }


}
