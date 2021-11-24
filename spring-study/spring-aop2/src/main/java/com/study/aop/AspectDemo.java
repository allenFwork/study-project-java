package com.study.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 定义切面
 */
@Component
@Aspect
public class AspectDemo {

    @Pointcut("execution(* com.study.dao.*.*(..))")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void before(){
        System.out.println("----------- AspectDemo before() ：proxy before... ----------------");
    }

}
