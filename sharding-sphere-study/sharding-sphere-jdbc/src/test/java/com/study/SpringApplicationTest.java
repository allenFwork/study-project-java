package com.study;

import com.study.service.SpringUseDemo;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationTest {

    /**
     * 测试 spring 整合 sharding-sphere 的使用
     */
    @Test
    public void test1() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        SpringUseDemo springUseDemo = context.getBean(SpringUseDemo.class);
        springUseDemo.use();
    }

}
