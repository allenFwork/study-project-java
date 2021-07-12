package com.study.controller;

import com.study.entity.User;
import com.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller是线程不安全的，所以在处理过程中一定要进行线程安全处理
 */
@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired // 可写，可不写
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user/save")
    public User user(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return user;
    }

}
