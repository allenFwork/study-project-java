package com.study.mapper;

import java.util.List;

public interface UserMapper2 {

    List<User> queryAll();

    User findByUserId(Integer userId);

    int saveUser(User user);

}

