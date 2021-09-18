package com.study.service.impl;

import com.study.dao.TransactionDao;
import com.study.entity.User;
import com.study.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务管理方法三:
 * 使用 DataSourceTransactionManager 对象
 */
@Service
public class TransactionServiceImpl3 implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    public void test1() {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionAttribute();
        defaultTransactionDefinition.setName("updateItemFeatureTransaction");
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置状态点
        TransactionStatus status = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
        try {
            System.out.println("执行逻辑开始 ...");
            test2();
            test3();
            System.out.println("执行逻辑结束 ...");

            // 提交事务
            dataSourceTransactionManager.commit(status);
        } catch (Exception e) {
            // 回滚事务到状态点
            dataSourceTransactionManager.rollback(status);
        }
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
