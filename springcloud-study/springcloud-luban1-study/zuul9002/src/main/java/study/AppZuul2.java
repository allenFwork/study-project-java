package study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关
 * zuul微服务作为路由
 */
@SpringBootApplication
@EnableZuulProxy // zuul和eureka整合过了，添加此注解就可以了，不需要添加eureka的客户端使用注解
// @EnableZuulServer
public class AppZuul2 {

    public static void main(String[] args) {
        SpringApplication.run(AppZuul2.class);
    }

}
