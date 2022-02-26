package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // 表示此微服务为 Eureka客户端
public class AppPowerServer {
    public static void main(String[] args) {
        SpringApplication.run(AppPowerServer.class);
    }
}
