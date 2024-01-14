package com.study.config;

import com.netflix.loadbalancer.*;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RibbonConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 配置 netflix的ribbon的负载均衡策略(客户端)
     *
     * @return
     */
//    @Bean
//    public IRule createIRule() {
//
//        /**
//         * 轮询策略(默认):
//         *   按照顺序依次获取，每次都获取下一个，循环。
//         */
//        return new RoundRobinRule();
//
//        /**
//         * 权重轮询策略:
//         *   根据每个Provider的相应时间分配一个权重，相应时间越长，权重越小，被选中的可能性就越低。
//         *   一开始为轮询策略，并开启一个计时器，每30秒收集一次每个Provider的平均响应时间，
//         *   当信息足够时，给每个Provider附上一个权重，并按权重随机选择Provider，权重越高的Provider会被高概率选中。
//         */
////        return new WeightedResponseTimeRule();
//
//        /**
//         * 随机策略:
//         *   从Provider中随机选取一个。
//         */
////        return new RandomRule();
//
//        /**
//         * 最少并发数策略:
//         *   选在请求中并发量最小的Provider，排除熔断的Provider。
//         */
////        return new BestAvailableRule();
//
//        /**
//         * 重试策略:
//         *   轮询策略的增强版，区别在于，轮询策略当服务器不可用时，不会做任何处理，
//         *   而重试策略在服务不可用时会重新尝试集群中的其他节点。
//         */
////        return new RetryRule();
//
//        /**
//         * 可用性策略:
//         *   过滤掉性能差的Provider，过滤掉Eureka中处于一直连接失败的Provide。过滤掉高并发的Provider。
//         */
////        return new AvailabilityFilteringRule();
//
//        /**
//         * 区域敏感策略:
//         *   以一个区域为单位，一旦这个这个区域中的服务出现不可用，则丢弃整个区域，从其他区域中选取可用的Provider。
//         *   如果这个IP区域中有一个或多个实例不可达或响应变慢，都会降低该区域内其他IPs被选中的权重。
//         */
////        return new ZoneAvoidanceRule();
//
//    }

}
