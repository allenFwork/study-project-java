package com.study.test;

import com.study.config.RedisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * 如果不想在自己的项目中配置任何类信息。例如下面的 “@ImportAutoConfiguration(value = {RedisAutoConfiguration.class})”
 * 也可以在自定义的 xxx-autoconfiguration 工程Resources中添加 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 文件，
 * 在其中配置 com.study.config.RedisAutoConfiguration。
 * 这样在自己的主项目中就不需要配置任何的信息了
 */
@ImportAutoConfiguration(value = {RedisAutoConfiguration.class})
public class CustomizedTestRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomizedTestRedisApplication.class);
    }

}
