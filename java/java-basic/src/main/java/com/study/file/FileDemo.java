package com.study.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

/**
 * File类的使用学习
 * <p>
 * \: 表示转义字符
 * WINDOWS 使用 \ 作为目录层级划分
 * Linux 使用 / 作为目录层级划分
 */
public class FileDemo {

    // File对象的创建
    public static void createMethod() {

        // 1.根据字符串表示的路径,变成File对象: public File(String path)
        String pathName = "D:\\documents\\study\\test\\1.txt";
        File file1 = new File(pathName);
        // 文件路径名
        String pathName2 = "D:\\documents\\study\\test\\2.txt";
        File file2 = new File(pathName2);

        /**
         * 2.通过父路径名字符串和子路径名字符串创建新的 File对象: public File(String parent, String child)
         *   父级路径:
         *   子级路径:
         * 注意：当涉及到路劲拼接时,最好使用该方法,能够保证间隔符在不同的操作系统中都正确
         */
        String parent = "D:\\documents\\study";
        String childPath = "test\\2.txt";
        File file3 = new File(parent, childPath);

        /**
         * 3.把一个 File表示的路径 和 String表示的路径进行拼接, 创建 File对象：public File(File parent, String child)
         */
        File parentDir = new File("D:\\documents\\study\\test");
        childPath = "1.txt";
        File file4 = new File(parentDir, childPath);
    }

    // 基础方法的使用1
    public static void basicMethod() {

        File file = new File("D:\\documents\\study\\test\\1.txt");
        File file2 = new File("D:\\documents\\study\\test");

        // 1.判断该文件是否是目录
        System.out.println(file.isDirectory()); //false
        System.out.println(file2.isDirectory()); //true
        // 是否是文件
        System.out.println(file.isFile()); //true
        System.out.println(file2.isFile()); //false
        // 是否存在
        System.out.println(file.exists()); //true
        System.out.println(file2.exists()); //true

        /**
         * 2.返回文件的大小（字节数量）: public long length();
         * 注意：
         *    该方法只能获取文件的大小,单位是字节
         *    该方法无法获取目录(文件夹)的大小,如果需要获取一个目录的大小,需要把该目录下所有的文件大小累加在一起
         */
        System.out.println("文件的字节大小：" + file.length());  //文件的字节大小：15
        System.out.println("目录的字节大小：" + file2.length()); //目录的字节大小：0

        // 3.返回文件的绝对路径:
        System.out.println("文件的绝对路径：" + file.getAbsolutePath()); //文件的绝对路径：D:\documents\study\test\1.txt
        System.out.println("目录的绝对路径：" + file2.getAbsolutePath());//目录的绝对路径：D:\documents\study\test
        // 通过 模块名 + 文件名, 可以直接获取该模块下的文件 (此处该文件放在该模块的resources目录下)
        File file3 = new File("java-basic\\file_path_test.txt");
        // 下面这个绝对路径是硬盘上不存在的路径
        System.out.println(file3.getAbsolutePath()); //D:\programme\idea\idea_workspace\study-project-java\java-basic\file_path_test.txt

        // 4.返回文件定义时使用的路径
        System.out.println(file.getPath());  //D:\documents\study\test\1.txt
        System.out.println(file2.getPath()); //D:\documents\study\test
        System.out.println(file3.getPath()); //java-basic\file_path_test.txt

        // 5.返回文件的名字
        System.out.println(file.getName()); //1.txt
        System.out.println(file2.getName());//test

        // 6.返回文件的最后修改时间（单位毫秒）
        System.out.println(file.lastModified()); //1694683962572
    }

    // 基础方法的使用2
    public static void basicMethod2() throws IOException {

        /**
         * 1.当且仅当具有该名称的文件尚不存在时，创建一个新的空文件
         *   细节1：如果当前路径表示的文件是不存在的，则创建成功，方法成功返回true
         *         如果当前路径表示的文件是存在的，则会创建失败，方法返回false
         *   细节2：如果父级路径是不存在的，那么方法会报异常：IOException
         *   细节3：该方法创建的一定是文件，如果路径中不包含后缀名，则创建一个没有后缀的文件
         */
        File file = new File("D:\\documents\\study\\test\\3.txt");
        boolean createFlag = file.createNewFile();
        System.out.println(createFlag);

        /**
         * 2.创建目录（文件夹）
         *   细节1：windows下路径是唯一的，如果当前路径已经存在，则会创建失败，返回false
         *   细节2：mkdir方法只能创建单级目录，无法创建多级目录
         */
        File directory = new File("D:\\documents\\study\\test\\parent");
        createFlag = directory.mkdir();
        System.out.println(createFlag);

        /**
         * 3.创建目录（文件夹）
         *   细节：即可以创建单级目录，又可以创建多级目录
         */
        File directoryS = new File("D:\\documents\\study\\test\\parent\\children\\children1");
        createFlag = directoryS.mkdir();
        System.out.println(createFlag);

        /**
         *  4.删除文件
         *    细节：
         *      如果删除的是文件，直接删除，不走回收站
         *      如果删除的是空目录(空文件夹)，直接删除，不走回收站
         *      如果删除的是有内容的目录（有内容的文件夹），则会删除失败
         */
        boolean deleteFlag = file.delete();
        System.out.println(deleteFlag);

    }

    // 目录的遍历
    public static void directoryTraverse() {

        // 1.获取系统中所有的盘符：public static File[] listRoots()
        File[] fileArr = File.listRoots();
        System.out.println(Arrays.toString(fileArr));

        File directory = new File("D:\\documents\\study\\test");

        // 2.获取当前目录下所有的文件和目录的名称（仅仅能获取名字）
        String[] names = directory.list();
        for (String name : names) {
            System.out.println(name);
        }

        // 3.利用文件名过滤获取指定路径下所有的内容：public String[] list(FilenameFilter filter)
        names = directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File file = new File(dir, name);
                return file.isFile() && name.endsWith(".txt");
            }
        });
        System.out.println(Arrays.toString(names));

        /**
         * 4.获取当前目录下所有文件和目录 对应的File对象 (重点)
         *  细节：
         *      当调用者File表示的路径不存在时，返回null
         *      当调用者File表示的路径是文件时，返回null
         *      当调用者File表示的路径是一个空文件夹时，返回一个长度为0的数组
         *      当调用者File表示的路径是一个有内容的文件夹时，将里面的所有文件和文件夹的路径放在File数组中返回
         *      当调用者File表示的路径是一个有隐藏文件的文件夹时，将里面的所有文件和文件夹的路径放在File数组中返回，包含隐藏文件
         *      当调用者File表示的路径是需要权限才能访问的文件夹时，返回null
         */
        File[] files = directory.listFiles();
        for (File file : files) {
            System.out.println(file);
        }

        // 文件名过滤：public File[] listFiles(FilenameFilter filter)
        files = directory.listFiles(new FilenameFilter() {
            @Override // 第一个参数是父级路径，第二个参数是该目录下的文件(或目录)的名字
            public boolean accept(File dir, String name) {
                System.out.println("name：" + name);
                System.out.println(dir.getAbsoluteFile());
                File file = new File(dir, name); //目录下的每一个文件（或目录）
                return file.isFile() && name.endsWith(".txt");
            }
        });
        System.out.println(Arrays.toString(files));

        // 文件过滤：public File[] listFiles(FileFilter filter)
        files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".txt");
            }
        });
        System.out.println(Arrays.toString(files));

    }

    public static void main(String[] args) throws IOException {

        //        /*
//         * 在内存中创建了一个File对象:
//         *   1. File对象及可以表示目录，也可以表示文件
//         *   2. File对象只能操作文件、目录的创建时间、名称等，不能访问内容
//         */
//        File file = new File("demo" + File.separator + "HelloWorld.txt");
//        System.out.println(file);
//        System.out.println("是否是文件：" + file.isFile());
//        System.out.println("是否是文件：" + file.exists());

        //createMethod();
        //basicMethod();
        //basicMethod2();
        directoryTraverse();

    }

}
