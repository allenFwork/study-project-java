package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "restTemplate")
    public RestTemplate createRestTemplate() {
        // 设置restTemplate的超市配置工厂对象
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置连接超时时间为2秒
        requestFactory.setConnectTimeout(2000);
        // 设置读取数据超时时间为2秒
        requestFactory.setReadTimeout(2000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

}
