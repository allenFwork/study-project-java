package com.study.repository;

import com.study.repository.UserRepository2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.study.config.SpringDataJpaConfig.class)
public class UserRepository2Test {

    @Autowired
    private UserRepository2 userRepository2;

    @Test
    public void findByIdTest() {
        Optional optional = userRepository2.findById("40289f0c65674a930165674d54940000");
        Assert.assertNotNull(optional.get());
    }

}
