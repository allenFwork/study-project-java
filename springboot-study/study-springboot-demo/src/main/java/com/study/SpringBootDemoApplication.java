package com.study;

import com.study.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @SpringBootApplication 等价于：
 * @SpringBootConfiguration
 * @EnableAutoConfiguration
 * @ComponentScan
 */
@SpringBootApplication
// 启用 @ConfigurationProperties 注解
@EnableConfigurationProperties(value = User.class)
//public class SpringBootDemoApplication extends SpringBootServletInitializer {
public class SpringBootDemoApplication {
    /**
     * springboot项目启动默认使用application.yml的配置文件，
     * 即使没有application.yml该文件，也不会直接使用application-dev.yml文件作为配置文件
     */
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootDemoApplication.class);
        springApplication.run(args);
    }

    /**
     * 让该springboot项目打包成war包，具体步骤：
     *  1. pom中配置 <packing>war</packing>
     *  2. 项目启动类继承 SpringBootServletInitializer类，重写 configure(SpringApplicationBuilder builder) 方法
     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(SpringBootDemoApplication.class);
//    }

}
