package com.study.feign.clients;

import com.study.feign.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-user-service") // 从注册中心获取该服务的具体信息
public interface UserClient {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);

}
