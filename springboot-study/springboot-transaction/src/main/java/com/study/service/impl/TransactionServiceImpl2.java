package com.study.service.impl;

import com.study.dao.TransactionDao;
import com.study.entity.User;
import com.study.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 事务管理方法二:
 * 使用 TransactionTemplate 对象
 */
@Service
public class TransactionServiceImpl2 implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void test1() {
        boolean flag = (boolean) transactionTemplate.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                try {
                    System.out.println("执行调用开始 ... ");
                    test2();
                    test3();
                    System.out.println("执行调用结束 ... ");
                    // 执行成功事务自动提交
                    return true;
                } catch (Exception e) {
                    // 进行事务回滚
                    transactionStatus.setRollbackOnly();
                    return false;
                }
            }
        });
    }

    public void test2() {
        System.out.println("执行插入 ... ");
        User user = new User();
        user.setId(9);
        user.setName("9");
        user.setPower("9");
        int i = transactionDao.insert(user);
        System.out.println(i);

    }

    public void test3() {
        System.out.println("执行更新 ... ");
        User user = new User();
        user.setId(9);
        user.setName("10");
        user.setPower("10");
        int i = transactionDao.update(user);
        System.out.println(i);
        int count = 1 / 0;
    }

    @Override
    public void test4() {

    }

}
