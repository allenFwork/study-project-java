package com.study.io.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamTest {

    // 拷贝文件夹
    public static void copyDir(File src, File dest) {
        dest.mkdirs();
        for (File file : src.listFiles()) {
            if (file.isFile()) {
                try (FileInputStream fis = new FileInputStream(file); FileOutputStream fos = new FileOutputStream(new File(dest, file.getName()))) {
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                copyDir(file, new File(dest, file.getName()));
            }
        }
    }

    // 文件加密解密
    public static void encryptionAndReduction(File src, File dest) {
        try (FileInputStream fis = new FileInputStream(src); FileOutputStream fos = new FileOutputStream(dest)) {
            int buffer;
            while ((buffer = fis.read()) != -1) {
                fos.write(buffer ^ 2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        File src = new File("D:\\documents\\study\\test\\io");
        File dest = new File("D:\\documents\\study\\test\\io2");
//        copyDir(src, dest);

        src = new File("D:\\documents\\study\\test\\io2\\2.txt");
        dest = new File("D:\\documents\\study\\test\\io2\\2_1.txt");
        // 生成加密后的文件
        encryptionAndReduction(src, dest);
        src = new File("D:\\documents\\study\\test\\io2\\2_2.txt");
        // 生成解密后的文件
        encryptionAndReduction(dest, src);
    }

}
