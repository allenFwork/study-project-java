package com.study.service;

import java.util.List;

public interface AccountService {

    void addAccount(String name, int initMoney);

    List<Account> queryAccount(String name);

    int updateAccount(String name, int money);

}
