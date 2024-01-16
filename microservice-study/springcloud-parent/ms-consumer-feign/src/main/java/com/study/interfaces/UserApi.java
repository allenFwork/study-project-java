package com.study.interfaces;

import com.study.config.FeignClientConfig;
import com.study.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "ms-provider-user", configuration = FeignClientConfig.class, path = "/user")
public interface UserApi {

    @RequestMapping("/queryUsersByUserId/{userId}")
    List<User> queryUserByUserId(@PathVariable("userId") Integer userId);

}
