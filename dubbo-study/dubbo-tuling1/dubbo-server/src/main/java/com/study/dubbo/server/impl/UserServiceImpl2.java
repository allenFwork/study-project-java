package com.study.dubbo.server.impl;

import com.study.dubbo.server.Bean.UserVo;
import com.study.dubbo.server.UserService;

import java.util.Date;

/**
 * 用于测试 jdk 的 spi 功能
 * service provide interface
 */
public class UserServiceImpl2 implements UserService {
    @Override
    public UserVo getUser(Integer id) {
        UserVo u = new UserVo();
        u.setBirthDay(new Date());
        u.setId(id);
        return u;
    }
}
