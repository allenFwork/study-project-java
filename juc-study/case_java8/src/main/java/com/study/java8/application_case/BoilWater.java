package com.study.java8.application_case;

import com.study.java8.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import static com.study.java8.util.Sleeper.sleep;

/**
 * 应用篇-统筹：烧开水案例
 */
@Slf4j(topic = "c.BoilWater")
public class BoilWater {

    public static void main(String[] args) {
        // 模拟第一个人做事：洗水壶、烧开水（此处假设总过花了6秒）
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            Sleeper.sleep(1);
            log.debug("烧开水");
            Sleeper.sleep(5);
        }, "老王");

        // 模拟第二个人做事：洗茶壶、洗茶杯、拿茶叶（此处假设总过花了5秒）
        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶");
            Sleeper.sleep(1);
            log.debug("洗茶杯");
            Sleeper.sleep(2);
            log.debug("拿茶叶");
            Sleeper.sleep(1);
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("泡茶");
        }, "小王");

        t1.start();
        t2.start();
    }
}
