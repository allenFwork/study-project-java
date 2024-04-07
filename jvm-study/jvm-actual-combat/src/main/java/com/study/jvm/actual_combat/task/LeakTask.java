package com.study.jvm.actual_combat.task;

import com.study.jvm.actual_combat.leakdemo.demo4.Outer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeakTask {
    private int count = 0;
    private List<Object> list = new ArrayList<>();

    @Scheduled(fixedRate = 100L) // 每隔100毫秒执行一次
    public void test() {
        System.out.println("定时任务调用：" + ++count);
        list.add(new Outer().newList());
    }
}
