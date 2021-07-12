package com.study;

import com.study.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImplTest {

    @Test
    public void createUser() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-tx.xml");
        UserService userService = context.getBean(UserService.class);
        userService.createUser("batman");
    }

}
