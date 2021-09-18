package com.study.service.impl;

import com.study.dao.TransactionDao;
import com.study.entity.User;
import com.study.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 事务管理方法一:
 *   使用声明式事务管理 @Transactional
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    // @Scheduled不生效原因，启动类上没有使用 @EnableScheduling 注解
    // @Scheduled(cron = "0 * * * * *")
    public void test1() {
        try {
            System.out.println("1111");
            List<User> list = transactionDao.selectAll();
            System.out.println(list);
        } catch (Exception e) {
            throw new RuntimeException("出现了异常");
        }
    }

    @Transactional
    public void test2() {
        try {
            System.out.println("执行插入 ... ");
            User user = new User();
            user.setId(9);
            user.setName("9");
            user.setPower("9");
            int i = transactionDao.insert(user);
            System.out.println(i);
//            int count = 1 / 0;
        } catch (Exception e) {
            throw new RuntimeException("出现了异常");
        }
    }

    @Transactional
    public void test3() {
        try {
            System.out.println("执行更新 ... ");
            User user = new User();
            user.setId(9);
            user.setName("10");
            user.setPower("10");
            int i = transactionDao.update(user);
            System.out.println(i);
        } catch (Exception e) {
            throw new RuntimeException("出现了异常");
        }
    }

    public void test22() {
        System.out.println("执行插入 ... ");
        User user = new User();
        user.setId(9);
        user.setName("9");
        user.setPower("9");
        int i = transactionDao.insert(user);
        System.out.println(i);

    }

    public void test33() {
        System.out.println("执行更新 ... ");
        User user = new User();
        user.setId(9);
        user.setName("10");
        user.setPower("10");
        int i = transactionDao.update(user);
        System.out.println(i);
        int count = 1 / 0;
    }

    /**
     * 计时 test22 和 test33 没有添加 @Transactional，
     * 此时他们也是公用一个事务的，由test4的事务管理的
     */
    @Transactional
    public void test4() {
        try {
            System.out.println("执行调用开始 ... ");
            test22();
            test33();
            System.out.println("执行调用结束 ... ");
        } catch (Exception e) {
            throw new RuntimeException("出现了异常");
        }
    }

}
