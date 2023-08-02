package com.study.redis.apply.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.redis.apply.dto.LoginFormDTO;
import com.study.redis.apply.dto.Result;
import com.study.redis.apply.entity.User;

import javax.servlet.http.HttpSession;

/**
 *  服务类
 */
public interface IUserService extends IService<User> {

    Result sendCode(String phone, HttpSession session);

    Result login(LoginFormDTO loginForm, HttpSession session);

}
