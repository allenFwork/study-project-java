package com.study.java8.pattern;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static com.study.java8.pattern.Downloader.*;

/**
 * 设计模式: 同步模式之保护性暂停 (版本1)
 */
@Slf4j(topic = "c.TestGuardedObject")
public class TestGuardedObject {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            try {
                // 子线程执行下载
                List<String> response = download();
                log.debug("download complete...");
                guardedObject.complete(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        log.debug("waiting...");
        // 主线程阻塞等待
        Object response = guardedObject.get();
        log.debug("get response: [{}] lines", ((List<String>) response).size());
    }
}

class GuardedObject {

    // 结果
    private Object response;
    private final Object lock = new Object();

    public Object get() {
        synchronized (lock) {
            // 条件不满足则等待
            while (response == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public void complete(Object response) {
        synchronized (lock) {
            // 条件满足，通知等待线程
            this.response = response;
            lock.notifyAll();
        }
    }
}