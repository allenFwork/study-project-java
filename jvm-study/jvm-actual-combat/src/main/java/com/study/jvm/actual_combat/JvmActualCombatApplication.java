package com.study.jvm.actual_combat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//-Xmx100m -Xms100m  -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:/documents/temp/heapdemo.hprof
//@EnableScheduling
@SpringBootApplication
public class JvmActualCombatApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(JvmActualCombatApplication.class, args);
        // run.getBean("TestLazy"); 懒加载，要获取该对象时，才创建了该Bean对象
    }

}