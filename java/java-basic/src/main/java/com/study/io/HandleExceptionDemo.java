package com.study.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HandleExceptionDemo {

    // 异常处理（方法1：JDK7以前版本）
    public static void exceptionProcess1() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("D:\\documents\\study\\test\\3.txt");
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 异常处理 (方法2：JDK7优化)
    public static void exceptionProcess2() {
        try (FileInputStream fis = new FileInputStream("D:\\documents\\study\\test\\3.txt"); FileOutputStream fos = new FileOutputStream("D:\\documents\\study\\test\\3.txt", true)) {
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, len));
            }
            fos.write("\n闪电侠正在归来".getBytes());
            // 通过 该死循环判断 fis 和 fos 是否释放了资源（这里死循环肯定没有释放资源）
            while (true) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 通过 该死循环判断 fis 和 fos 是否释放了资源（这里死循环可以看出释放了资源）
        while (true) {
        }
    }

    // 异常处理 (方法3：JDK9的改进)
    public static void exceptionProcess3() {

    }

    public static void main(String[] args) {
//        exceptionProcess1();
        exceptionProcess2();
    }

}
