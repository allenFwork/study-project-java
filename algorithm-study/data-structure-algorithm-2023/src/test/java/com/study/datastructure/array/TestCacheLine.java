package com.study.datastructure.array;

import org.springframework.util.StopWatch;

public class TestCacheLine {

    public static void ij(int[][] a, int rows, int columns) {
        long sum = 0L;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sum += a[i][j];
            }
        }
        System.out.println(sum);
    }

    public static void ji(int[][] a, int rows, int columns) {
        long sum = 0L;
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                sum += a[i][j];
            }
        }
        System.out.println(sum);
    }

    /*
          CPU                     缓存                                   内存
        运算速度（皮秒级别）                                               纳秒级别
                                 每次放入64字节
                                 这64个字节被称为缓存行 cache line

                                 空间局部性
     */
    public static void main(String[] args) {
        int rows = 1_000_000;
        int columns = 14;
        int[][] a = new int[rows][columns]; // 设置了一个比较大的二维数组，但是没有设置初值，默认都是0

        // 使用 Spring 的 org.springframework.util.StopWatch 测试执行效率
        StopWatch sw = new StopWatch();

        sw.start("ij");
        ij(a, rows, columns);
        sw.stop();

        sw.start("ji");
        ji(a, rows, columns);
        sw.stop();

        System.out.println(sw.prettyPrint());
        /*
         * 执行结果：
         * 0
         * 0
         * StopWatch '': running time = 130276100 ns
         * ---------------------------------------------
         * ns         %     Task name
         * ---------------------------------------------
         * 027578700  021%  ij
         * 102697400  079%  ji
         *
         * 其中总耗时 130276100 纳秒，其中 ji 占用了21%，花费 27578700 纳秒。性能更好
         */
    }
}
