package com.study.java8.n2;

import com.study.java8.Constants;
import com.study.java8.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步 不等待
 */
@Slf4j(topic = "c.Async")
public class Async {

    public static void main(String[] args) {
        new Thread(() -> FileReader.read(Constants.MP4_FULL_PATH)).start();
        log.debug("do other things ...");
    }

}
