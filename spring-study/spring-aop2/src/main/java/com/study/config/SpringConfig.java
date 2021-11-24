package com.study.config;

import com.study.annotation.EnableUtil;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.study")
@EnableAspectJAutoProxy
@EnableUtil(userName = "AllenWork", time = 1000)
public class SpringConfig {
}
