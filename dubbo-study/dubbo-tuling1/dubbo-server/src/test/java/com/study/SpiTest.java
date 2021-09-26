package com.study;

import com.study.dubbo.server.UserService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Tommy
 * Created by Tommy on 2018/11/23
 **/
public class SpiTest {
    public static void main(String[] args) {
        // java 的 SPI: java.util.ServiceLoader
        Iterator<UserService> iterator = ServiceLoader.load(UserService.class).iterator();
        while(iterator.hasNext()) {
            UserService userService = iterator.next();
            System.out.println(userService.getUser(111));
        }
        // JDBC  mysq Driver
//        try {
//            Class.forName("com.mysql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
