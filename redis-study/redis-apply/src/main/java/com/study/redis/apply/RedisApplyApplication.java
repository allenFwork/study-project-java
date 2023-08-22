package com.study.redis.apply;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * redis的相关具体应用： 点评项目服务
 */
@MapperScan("com.study.redis.apply.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true) // 暴露AspectJ的代理对象，默认是不暴露
public class RedisApplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplyApplication.class, args);
    }

}