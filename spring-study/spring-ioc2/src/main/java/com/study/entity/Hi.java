package com.study.entity;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Hi implements InitializingBean, DisposableBean {
    public void sayHi(){
        System.out.println("Hi");
    }

    public void destroy() throws Exception {

    }

    public void afterPropertiesSet() throws Exception {

    }
}
