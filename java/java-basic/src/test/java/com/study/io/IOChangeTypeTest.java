package com.study.io;

import org.junit.Test;

import java.io.File;

public class IOChangeTypeTest {

    @Test
    public void test() {
        String path = "Z:\\document\\工作数据.txt";
        byte[] bytes = IOChangeType.FileToByteArray(path);
        System.out.println(bytes); // [B@50134894

        File file = new File("Z:\\document\\工作数据2.txt");
        IOChangeType.ByteArrayToFile(bytes, file);
    }

}
