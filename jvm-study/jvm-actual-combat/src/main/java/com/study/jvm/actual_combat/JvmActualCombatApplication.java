package com.study.jvm.actual_combat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//-Xmx100m -Xms100m  -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:/jvm/heapdemo.hprof
//@EnableScheduling
@SpringBootApplication
public class JvmActualCombatApplication {

    public static void main(String[] args) {
        SpringApplication.run(JvmActualCombatApplication.class, args);
    }

}