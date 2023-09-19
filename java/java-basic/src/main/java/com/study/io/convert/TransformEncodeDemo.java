package com.study.io.convert;

import java.io.*;

/**
 * 通过转换流进行文件编码格式的转化
 * 转换流：InputStreamReader 和 OutputStreamWriter
 */
public class TransformEncodeDemo {

    public static void changeFileEncode(File src, File dest, String encode, String changeEncode) throws IOException {
        // 创建对象 (InputStreamReader和 OutputStreamWriter转换流的构造方法中，传入字节流对象，实现 字符-字节 的转换)
        InputStreamReader isr = new InputStreamReader(new FileInputStream(src), encode);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(dest), changeEncode);

        // 3.读写数据
        int len;
        char[] buffer = new char[1024];
        while ((len = isr.read(buffer)) != -1) {
            osw.write(buffer, 0, len);
        }

        // 释放资源
        osw.close();
        isr.close();
    }

    public static void main(String[] args) {

    }

}
