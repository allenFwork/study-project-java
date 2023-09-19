package com.study.io.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩流 和 解压缩流
 */
public class ZipStreamDemo {

    /**
     * 定一个方法来压缩文件
     *
     * @param src           表示要压缩的文件
     * @param dest          表示压缩包的位置
     * @param directoryList 压缩包内指定的目录层级
     * @throws IOException
     */
    public static void toZip(File src, File dest, String directoryList) throws IOException {
        // 1.创建压缩流关联压缩包
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(dest, "zip_test.zip")));
        // 2.创建ZipEntry对象，表示压缩包里面的每一个文件和文件夹。参数：压缩包里面的路径
        ZipEntry entry = new ZipEntry(directoryList + src.getName());
        // 3.把ZipEntry对象放到压缩包当中
        zos.putNextEntry(entry);
        // 4.把src文件中的数据写到压缩包当中
        FileInputStream fis = new FileInputStream(src);
        int b;
        while ((b = fis.read()) != -1) {
            zos.write(b);
        }
        zos.closeEntry();
        zos.close();
    }

    /**
     * 定义一个方法用来解压文件
     * 解压的本质：把压缩包里面的每一个文件或者文件夹读取出来，按照层级拷贝到目的地当中
     *
     * @param src  要解压的压缩包文件
     * @param dest 解压的目的地
     * @throws IOException
     */
    public static void unZip(File src, File dest) throws IOException {
        //创建一个解压缩流用来读取压缩包中的数据
        ZipInputStream zip = new ZipInputStream(new FileInputStream(src));
        //要先获取到压缩包里面的每一个ZipEntry对象, 表示当前在压缩包中获取到的文件或者文件夹
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            System.out.println(entry);
            if (entry.isDirectory()) {
                //文件夹：需要在目的地dest处创建一个同样的文件夹
                File file = new File(dest, entry.toString());
                file.mkdirs();
            } else {
                // 文件：需要读取到压缩包中的文件，并把他存放到目的地dest文件夹中（按照层级目录进行存放）
                FileOutputStream fos = new FileOutputStream(new File(dest, entry.toString()));
                int b;
                while ((b = zip.read()) != -1) {
                    //写到目的地
                    fos.write(b);
                }
                fos.close();
                //表示在压缩包中的一个文件处理完毕了。
                zip.closeEntry();
            }
        }
        zip.close();
    }

    public static void main(String[] args) throws IOException {
        File file1 = new File("D:\\documents\\study\\test\\zip.txt");
        File file2 = new File("D:\\documents\\study\\test");
//        toZip(file1, file2, "目录层级1\\目录层级2\\");
        toZip(file1, file2, "");

        file1 = new File("D:\\documents\\study\\test\\zip_test.zip");
        file2 = new File("D:\\documents\\study\\test");
        unZip(file1, file2);
    }

}


