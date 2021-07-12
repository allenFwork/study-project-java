package com.study;

import com.study.service.AccountService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountServiceImplTest {
    @Test
    public void addAccount() throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-tx.xml");
        AccountService service = context.getBean(AccountService.class);
        service.addAccount("superman", 1000);
    }
}
