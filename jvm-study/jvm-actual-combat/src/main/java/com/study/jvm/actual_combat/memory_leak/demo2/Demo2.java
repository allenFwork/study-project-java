package com.study.jvm.actual_combat.memory_leak.demo2;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存泄漏原因：equals()和hashCode()导致的内存泄漏
 */
public class Demo2 {
    public static long count = 0;
    public static Map<Student, Long> map = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            if (count++ % 100 == 0) {
                // 休眠的原因：如果此程序一致处于运行状态，那么通过VisualVM监控该程序时，可能一直处于卡斯状态，让此程序将CPU时间片让出来
                Thread.sleep(10);
            }
            Thread.sleep(10);
            Student student = new Student();
            student.setId(1);
            student.setName("张三");
            map.put(student, 1L);
        }
    }
}
