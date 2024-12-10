package com.study.springboot.log.controller;

import com.study.springboot.log.annotation.Log;
import com.study.springboot.log.pojo.User;
import com.study.springboot.log.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Log(name = "通过Id查询用户信息的接口")
    @RequestMapping("/getById/{id}")
    @ResponseBody
    public User getById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody User user) {
        userService.save(user);
    }

    @PutMapping("/update")
    public void update(User user) {
        userService.update(user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }
}
