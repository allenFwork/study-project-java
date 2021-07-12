package com.study.service;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AccountService accountService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createUser(String name) {
        // 插入user 记录
        jdbcTemplate.update("INSERT INTO `user` (name) VALUES(?)", name);
        // 调用 accountService 添加帐户
        accountService.addAccount(name, 10000);

        // 下面这样调用，就等价于 this.addAccount(name, 1000);
        // 因为没有进行代理，是直接调用的，所以此时方法上的事务没有生效
//        addAccount(name, 1000);

        // 这样调用，事务就会生效
//        ((UserService) AopContext.currentProxy()).addAccount(name, 1000);

        // 人为报错
//        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addAccount(String name, int initMoney) {
        accountService.addAccount(name, initMoney);
    }

}
