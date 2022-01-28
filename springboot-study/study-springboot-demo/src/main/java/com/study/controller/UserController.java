package com.study.controller;

import com.study.entity.User;
import com.study.UserRepository;
import com.study.handler.CustomizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller是线程不安全的，所以在处理过程中一定要进行线程安全处理
 */
@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    private User user;

    @Autowired // 可写，可不写
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user/save")
    public User saveUser(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return user;
    }

    @GetMapping("/user/find")
    public User findUser(@RequestParam String name) {
        System.out.println(user);
        return user;
    }

    @GetMapping("/test/error")
    public User testError() throws CustomizedException {
        throw new CustomizedException(100, "测试错误");
    }

}
