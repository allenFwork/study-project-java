package com.study.controller;

import com.study.entity.User;
import com.study.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class UserController {

    private final UserRepository userRepository;

    // 常见线程池异步用来处理请求
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    // 通过构造方法注入，不需要使用 @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 同步方式
    @PostMapping("web/mvc/user/save")
    public Boolean saveUser(@RequestBody User user) {
        System.out.printf("[Thread : %s] start saving user ... \n", Thread.currentThread().getName());
        return userRepository.saveUser(user);
    }

    // 异步方式
    @PostMapping("web/mvc/user/saveByAsynchronous")
    public Boolean saveUserByAsynchronous(@RequestBody User user) throws ExecutionException, InterruptedException {
        System.out.printf("[Thread : %s] \n", Thread.currentThread().getName());
        Future<Boolean> future = executorService.submit(() -> {
            return userRepository.saveUser(user);
        });
        return future.get();
    }

    @PostMapping("jdbc/save")
    public Boolean saveUserByJdbc(@RequestBody User user) {
        return userRepository.jdbcSaveUser(user);
    }
}
