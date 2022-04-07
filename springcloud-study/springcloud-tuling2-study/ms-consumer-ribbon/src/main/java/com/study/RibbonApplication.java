package com.study;

import com.study.config.RibbonLoadBalanceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

// 如果 com.study.config.RibbonLoadBalanceConfig类使用了@Configuration注解,那么就需要使用下面的@ComponentScan注解进行去除掉
@RibbonClient(name = "ms-provider-user", configuration = RibbonLoadBalanceConfig.class)
@SpringBootApplication
public class RibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbonApplication.class);
    }

}
