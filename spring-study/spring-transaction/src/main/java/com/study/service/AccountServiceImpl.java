package com.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW) // 不支持当前事务，该方法创建新的事务
    public void addAccount(String name, int initMoney) {
        String accountId = new SimpleDateFormat("yyyyMMdd hh:mm:ss").format(new Date());
        jdbcTemplate.update("insert into account (accountName, user, money) values (?, ?, ?)", accountId, name, initMoney);
        // 人为报错
        int i = 1 / 0;
    }

    @Override
    @Transactional // 等价于 @Transactional(propagation = Propagation.REQUIRED) 支持当前事务
    public List<Account> queryAccount(String name) {
        List<Account> list = jdbcTemplate.queryForList("select * from account where user = ?", Account.class,name);
        Arrays.toString(list.toArray());
        return list;
    }

    @Override
    @Transactional
    public int updateAccount(String name, int money) {
        return jdbcTemplate.update("update account set money = money + 1 where user = ?", money, name);
    }

}
