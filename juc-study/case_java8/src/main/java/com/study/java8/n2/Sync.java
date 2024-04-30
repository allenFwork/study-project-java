package com.study.java8.n2;

import com.study.java8.Constants;
import com.study.java8.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步等待
 */
@Slf4j(topic = "c.Sync")
public class Sync {

    public static void main(String[] args) {
        FileReader.read(Constants.MP4_FULL_PATH); // 是一个同步调用，得等该方法返回才能向下执行
        log.debug("do other things ...");
    }

}
