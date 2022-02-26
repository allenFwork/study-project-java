package com.study.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import com.study.util.CustomerRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced // 使用ribbon组件实现客户端负载均衡
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 通过 TomcatServletWebServerFactory 配置tomcat容器的端口号
     * @return
     */
//    @Bean
//    public TomcatServletWebServerFactory createTomcatServletWebServerFactory() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.setPort(5000);
//        return tomcat;
//    }

//    /**
//     * 修改负载均衡策略
//     * 默认使用轮询策略
//     *
//     * 这里的均衡策略放在了 @ComponentScan之下，调用所有微服务斗勇这个策略
//     * @return
//     */
//    @Bean
//    public IRule createIRule() {
//        // 使用随机策略
////        return new RandomRule();
//        // 使用 RetryRule 策略
//        return new RetryRule();
//        // 使用自定义的随机策略
////        return  new CustomerRule();
//    }

}
