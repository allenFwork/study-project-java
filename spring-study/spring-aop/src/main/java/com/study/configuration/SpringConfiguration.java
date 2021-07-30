package com.study.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan("com.study")
@EnableAspectJAutoProxy // 开启 @AspectJ 注解，默认使用 JDK 的动态代理
public class SpringConfiguration {

}
