package com.study.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void hello(){
        System.out.println("this is UserService.hello()");
        hello2("private");
    }

    public String hello(String name){
        System.out.println("this is UserService.hello(String name)");
        hello2("private");
        return null;
    }

    public String hello2(String name){
        System.out.println("this is private String UserService.hello2(String name)");
        return null;
    }

    public String hello2(String name1, String name2){
        System.out.println("this is UserService.hello23(String name1, String name2)");
        return null;
    }
}
