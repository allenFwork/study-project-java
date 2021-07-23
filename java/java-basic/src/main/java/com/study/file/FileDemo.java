package com.study.file;

import java.io.File;

public class FileDemo {

    public static void main(String[] args) {
        /*
         * 在内存中创建了一个File对象:
         *   1. File对象及可以表示目录，也可以表示文件
         *   2. File对象只能操作文件、目录的创建时间、名称等，不能访问内容
         */
        File file = new File("demo" + File.separator + "HelloWorld.txt");
        System.out.println(file);
        System.out.println("是否是文件：" + file.isFile());
        System.out.println("是否是文件：" + file.exists());
    }

}
