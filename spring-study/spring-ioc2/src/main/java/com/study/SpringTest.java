package com.study;

import com.study.entity.DriverFactoryBean;
import com.study.entity.HelloSpring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.sql.Driver;

public class SpringTest {

    public static void main(String[] args) {
        // 加载文件系统中配置文件
//        ApplicationContext context =
//                new FileSystemXmlApplicationContext("C:\\Users\\86131\\IdeaProjects\\study-project-java\\spring-study\\spring-ioc2\\src\\main\\resources\\spring.xml");
        // 加载工程 classpath 下的配置文件实例化
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        HelloSpring helloSpring = (HelloSpring) context.getBean("springHello");
        DriverFactoryBean driverFactoryBean = context.getBean(DriverFactoryBean.class);
        Driver driver = (Driver) context.getBean("driver");
        com.mysql.cj.jdbc.Driver mysqlDriver = (com.mysql.cj.jdbc.Driver) context.getBean("driver");
    }

}
