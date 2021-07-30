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
     *  @Pointcut : 声明切点
     *  每个切点都要有对应的连接点，连接点就是需要添加代码进去的目标对象中的方法,
     *  execution()中的表达式 public * com.study.dao.*.*(..)：
     *    public    ：可写可不写，代表所有的
     *    第一个 *  : 表示返回类型，不能省略
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
