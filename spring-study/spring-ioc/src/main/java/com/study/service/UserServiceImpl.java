package com.study.service;

import com.study.dao.UserDao;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(){

    }

    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public List query() {
        return null;
    }

    @Override
    public void update() {

    }
}
