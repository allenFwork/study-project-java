package com.study.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * File类实现需求
 */
public class FileTestDemo {

    /**
     * 需求1：在当前模块下temp文件夹中创建a.txt文件
     */
    public static void test1() throws IOException {

        // 1.创建a.txt的父级路径
        File dirctory = new File("java-basic\\temp");
        // 2.创建父级路径
        // 如果 temp文件夹是存在的，那么此时创建就会失败；如果 temp文件夹是不存在的，那么此时创建就会成功；
        dirctory.mkdirs();
        // 3.拼接父级路径和自己路径
        File file = new File(dirctory, "a.txt");
        boolean createFlag = file.createNewFile();
        System.out.println(file.getAbsolutePath()); // D:\programme\idea\idea_workspace\study-project-java\java-basic\temp\a.txt
        if (createFlag)
            System.out.println("创建成功");
        else
            System.out.println("创建失败");
    }

    /**
     * 需求2：定义一个方法找到一个文件夹中，是否有以 avi 结尾的电影（暂时不考虑子文件夹）
     */
    public static void test2(String directoryPath, String postfix) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            String[] fileNameArr = directory.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    File file = new File(dir, name);
                    return file.isFile() && name.endsWith(postfix);
                }
            });
            System.out.println(Arrays.toString(fileNameArr));
        }
    }

    /**
     * 需求3：找到电脑中所有以avi结尾的电影。（需要考虑子文件夹）
     * <p>
     * 思考路线：1.进入文件夹 -> 2.遍历数组 -> 3.判断 -> 4.判断
     */
    public static void test3(File directory, String postfix) {
        // 1.进入文件夹（目录）
        File[] fileArr = directory.listFiles();
        // 存在 fileArr为null的情况，所以需求去除掉该情况的处理逻辑
        if (fileArr == null)
            return;
        // 2.遍历数组
        for (File file : fileArr) {
            // 3.判断：是否是文件，如果是文件，就可以执行对应的业务路基
            if (file.isFile()) {
                if (file.getName().endsWith(postfix))
                    System.out.println(file.getAbsolutePath());
            } else {
                /**
                 * 4.判断：是否是目录，如果是目录，就直接递归处理
                 * 细节：
                 *  1.再次调用此方法时，参数一定是directory的次一级目录路径
                 *  2.进入到else，肯定就是目录了，因为只有目录和文件两种形式
                 */
                test3(file, postfix);
            }
        }
    }

    /**
     * 需求4：如果我们要删除一个有内容的文件夹
     * 1.先删除文件夹里面所有的内容
     * 2.再删除自己
     */
    public static void test4(File directory) {
        // 1.先删除文件夹里所有的内容
        // 进入文件夹
        File[] fileArr = directory.listFiles();
        if (fileArr == null)
            return;
        // 遍历
        for (File file : fileArr) {
            // 判断，如果过是文件，直接删除
            if (file.isFile()) {
                file.delete();
            } else {
                // 判断，如果是文件夹，递归调用本函数
                test4(file);
            }
        }
        // 2.删除自己
        directory.delete();
    }

    /**
     * 统计一个文件夹的总大小
     */
    public static long test5(File directory) {
        // 1.定义变量进行累加
        long len = 0L;
        File[] fileArr = directory.listFiles();
        if (fileArr == null)
            return 0;
        for (File file : fileArr) {
            if (file.isFile()) {
                len += file.length();
            } else {
                len += test5(file);
            }
        }
        return len;
    }

    /**
     * 作用： 统计一个文件夹中每种文件的个数
     * 参数：要统计的那个文件夹
     * 返回值：
     * 用来统计map集合
     * 键：后缀名 值：次数
     * <p>
     * 文件名：a.txt、 a.a.txt、 aaa（不需要统计的）
     */
    public static HashMap<String, Integer> test6(File directory) {
        HashMap<String, Integer> map = new HashMap<>();
        File[] fileArr = directory.listFiles();
        if (fileArr == null) {
            return map;
        }
        for (File file : fileArr) {
            if (file.isFile()) {
                String[] nameArr = file.getName().split("\\.");
                if (nameArr.length >= 2) {
                    if (map.containsKey(nameArr[nameArr.length - 1])) {
                        map.put(nameArr[nameArr.length - 1], map.get(nameArr[nameArr.length - 1]) + 1);
                    } else {
                        map.put(nameArr[nameArr.length - 1], 1);
                    }
                }
            } else {
                HashMap<String, Integer> sonMap = test6(file);
                for (Map.Entry<String, Integer> stringIntegerEntry : sonMap.entrySet()) {
                    String key = stringIntegerEntry.getKey();
                    Integer value = stringIntegerEntry.getValue();
                    if (map.containsKey(key)) {
                        map.put(key, map.get(key) + value);
                    } else {
                        map.putIfAbsent(key, value);
                    }
                }
            }
        }
        return map;
    }



    public static void main(String[] args) throws IOException {

        // test1();
        // test2("D:\\documents\\study\\test", ".avi");

//        File[] directoryArr = File.listRoots();
//        for (File directory : directoryArr) {
//            test3(directory, ".avi");
//        }

//        File directory = new File("D:\\documents\\study\\test\\parent");
//        test4(directory);

//        File directory = new File("D:\\documents\\study\\test");
//        System.out.println(test5(directory) + "个字节");

        File directory = new File("D:\\documents\\study\\test");
        System.out.println(test6(directory).toString());

    }

}
