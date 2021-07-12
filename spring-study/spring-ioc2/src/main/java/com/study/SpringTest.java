package com.study;

import com.study.entity.DriverFactoryBean;
import com.study.entity.HelloSpring;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Driver;

public class SpringTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        HelloSpring helloSpring = (HelloSpring) context.getBean("springHello");
        DriverFactoryBean driverFactoryBean = context.getBean(DriverFactoryBean.class);
        Driver driver = (Driver) context.getBean("driver");
        com.mysql.cj.jdbc.Driver mysqlDriver = (com.mysql.cj.jdbc.Driver) context.getBean("driver");
    }

}
