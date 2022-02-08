package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ImportResource;

/**
 * 学习springboot地自动装配插件(redis,自定义插件等等)
 */
@SpringBootApplication
/*
 * 直接给接口上添加@Mapper注释或是在SpringBoot入口处，增加@MapperScan指定要扫描的包就能正常启动。
 * 通过MapperScan注解找到UserMapper2接口，对其进行代理交由spring管理
 */
@MapperScan(basePackages = {"com.study.mapper"})
// 通过xml配置对应的redis需要的bean对象，再通过 ImportResource注解 导入该配置文件
//@ImportResource(locations = "classpath:spring-redis.xml")
public class OpenAutoConfigPrincipleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAutoConfigPrincipleApplication.class);
    }

}
