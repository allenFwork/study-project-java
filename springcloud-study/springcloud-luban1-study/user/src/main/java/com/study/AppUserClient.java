package com.study;

import com.config.OrderIRuleConfig;
import com.config.PowerIRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient // 表示此微服务为 Eureka客户端
@RibbonClients({
        @RibbonClient(name = "SERVER-ORDER", configuration = OrderIRuleConfig.class),
        @RibbonClient(name = "SERVER-POWER", configuration = PowerIRuleConfig.class)
})
@EnableFeignClients // 声明此为服务是 feign 的客户端
@EnableHystrix // 表示此为服务使用hystrix断路器,老版本使用 @EnableCircuitBreaker 注解, EnableHystrix注解继承了EnableCircuitBreaker注解
public class AppUserClient {

    public static void main(String[] args) {
        SpringApplication.run(AppUserClient.class);
    }

}
