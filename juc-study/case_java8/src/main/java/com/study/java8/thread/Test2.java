package com.study.java8.thread;

import com.study.java8.Constants;
import com.study.java8.util.FileReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test2")
public class Test2 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
                FileReader.read(Constants.MP4_FULL_PATH);
            }
        };

        // 调用线程的run方法，此时还是使用主线程来执行该方法（同步处理，串行执行）
//        t1.run();
        // 调用线程的start方法，此时使用t1线程来执行该方法（异步处理，并行执行）
        t1.start();
        log.debug("do other things...");
    }
}
