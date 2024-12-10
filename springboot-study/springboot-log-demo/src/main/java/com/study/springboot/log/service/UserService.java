package com.study.springboot.log.service;

import com.study.springboot.log.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void save(User user) {
        //
        System.out.println(user);
    }

    public void update(User user) {
        System.out.println(user);
    }

    public void delete(Integer id) {
        System.out.println(id);
    }

    public User getById(Integer id) {
        return new User();
    }

    public User checkToken(String token) {
        return null;
    }
}
