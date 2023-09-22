package com.study.algorithm.binarysearch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.study.algorithm.binarysearch.BinarySearch.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestBinarySearch {

    @Test
    @DisplayName("测试 binarySearchBasic")
    public void test1() {
        int[] a = {7, 13, 21, 30, 38, 44, 52, 53};
        /*
         * assertEquals 是 org.junit.jupiter.api.Assertions类的静态方法。
         * 作用：判断前后两个值是否相等，不相等时，报 org.opentest4j.AssertionFailedError 错误
         */
        assertEquals(0, binarySearchBasic(a, 7));
        assertEquals(1, binarySearchBasic(a, 13));
        assertEquals(2, binarySearchBasic(a, 21));
        assertEquals(3, binarySearchBasic(a, 30));
        assertEquals(4, binarySearchBasic(a, 38));
        assertEquals(5, binarySearchBasic(a, 44));
        assertEquals(6, binarySearchBasic(a, 52));
        assertEquals(7, binarySearchBasic(a, 53));

        assertEquals(-1, binarySearchBasic(a, 0));
        assertEquals(-1, binarySearchBasic(a, 15));
        assertEquals(-1, binarySearchBasic(a, 60));
    }

    @Test
    @DisplayName("测试右移运算")
    public void test2() {
        int i = 0;
        int j = Integer.MAX_VALUE - 1;
        // 模拟第一次求中间索引
        int m = (i + j) / 2;
        // 模拟第二次求中间索引, target 在右侧
        i = m + 1;
        assertEquals(1073741824, i);
        assertEquals(2147483646, j);
        assertEquals(-1073741826, i + j);
        m = (i + j) / 2;   // 有问题的情况: i+j超过了Integer的最大值范围
        assertEquals(-536870913, m);
        m = (i + j) >>> 1; // 改正后的情况，无符号右移1位，第一位直接补0 (>> 有符号右移，正数补0，负数补1)
        assertEquals(1610612735, m);

        /**
         * 分析：
         * 1073741824 对应的二进制数：0100_0000_0000_0000_0000_0000_0000_0000
         * 2147483646 对应的二进制数：0111_1111_1111_1111_1111_1111_1111_1110
         * 3221225470 对应的二进制数：1011_1111_1111_1111_1111_1111_1111_1110 （上面连个数相加得到）
         *
         * 对于同一个二进制数 "1011_1111_1111_1111_1111_1111_1111_1110"
         * 1.不把最高位视为符号位, 则代表 3221225470
         * 2.把最高位视为符号位,  则代表 -1073741826
         *
         * Integer是一个有符号的数值，所以上面两个数相加后，得到-1073741826，这是错误的，用该数值除以2 得到下面这个数（最高位是1）：
         * 1101_1111_1111_1111_1111_1111_1111_1111，表示 -536870913.
         *
         * 使用 ”>>>“，表示进行无符号位右移运算：>>>1 右移一位，最高位直接补0，
         * 即 0101_1111_1111_1111_1111_1111_1111_1111，表示 1610612735.
         */
        int num = 0b1101_1111_1111_1111_1111_1111_1111_1111;
        System.out.println(num);//-536870913
        num = 0b0101_1111_1111_1111_1111_1111_1111_1111;
        System.out.println(num);//1610612735

    }

    @Test
    @DisplayName("测试 binarySearchAlternative ")
    public void test3() {
        int[] a = {7, 13, 21, 30, 38, 44, 52, 53};
        assertEquals(0, binarySearchAlternative(a, 7));
        assertEquals(1, binarySearchAlternative(a, 13));
        assertEquals(2, binarySearchAlternative(a, 21));
        assertEquals(3, binarySearchAlternative(a, 30));
        assertEquals(4, binarySearchAlternative(a, 38));
        assertEquals(5, binarySearchAlternative(a, 44));
        assertEquals(6, binarySearchAlternative(a, 52));
        assertEquals(7, binarySearchAlternative(a, 53));

        assertEquals(-1, binarySearchAlternative(a, 0));
        assertEquals(-1, binarySearchAlternative(a, 15));
        assertEquals(-1, binarySearchAlternative(a, 60));
    }

    @Test
    @DisplayName("测试 binarySearchBalance")
    public void test4() {
        int[] a = {7, 13, 21, 30, 38, 44, 52, 53};
        assertEquals(0, binarySearchBalance(a, 7));
        assertEquals(1, binarySearchBalance(a, 13));
        assertEquals(2, binarySearchBalance(a, 21));
        assertEquals(3, binarySearchBalance(a, 30));
        assertEquals(4, binarySearchBalance(a, 38));
        assertEquals(5, binarySearchBalance(a, 44));
        assertEquals(6, binarySearchBalance(a, 52));
        assertEquals(7, binarySearchBalance(a, 53));

        assertEquals(-1, binarySearchBalance(a, 0));
        assertEquals(-1, binarySearchBalance(a, 15));
        assertEquals(-1, binarySearchBalance(a, 60));
    }

    @Test
    @DisplayName("测试 binarySearch java 版")
    public void test5() {
        /*
                ⬇
            [2, 5, 8]       a
            [2, 0, 0, 0]    b
            [2, 4, 0, 0]    b
            [2, 4, 5, 8]    b
         */
        int[] a = {2, 5, 8};
        int target = 4;
        int i = Arrays.binarySearch(a, target);
        assertTrue(i < 0);
        // i = -插入点 - 1  因此有 插入点 = abs(i+1)
        int insertIndex = Math.abs(i + 1); // 插入点索引
        int[] b = new int[a.length + 1];
        System.arraycopy(a, 0, b, 0, insertIndex);
        b[insertIndex] = target;
        System.arraycopy(a, insertIndex, b, insertIndex + 1, a.length - insertIndex);
        assertArrayEquals(new int[]{2, 4, 5, 8}, b);
    }


    @Test
    @DisplayName("测试 binarySearchLeftmost 返回 -1")
    public void test6() {
        int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
        assertEquals(0, binarySearchLeftmost1(a, 1));
        assertEquals(1, binarySearchLeftmost1(a, 2));
        assertEquals(2, binarySearchLeftmost1(a, 4));
        assertEquals(5, binarySearchLeftmost1(a, 5));
        assertEquals(6, binarySearchLeftmost1(a, 6));
        assertEquals(7, binarySearchLeftmost1(a, 7));

        assertEquals(-1, binarySearchLeftmost1(a, 0));
        assertEquals(-1, binarySearchLeftmost1(a, 3));
        assertEquals(-1, binarySearchLeftmost1(a, 8));
    }

    @Test
    @DisplayName("测试 binarySearchRightmost 返回 -1")
    public void test7() {
        int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
        assertEquals(0, binarySearchRightmost1(a, 1));
        assertEquals(1, binarySearchRightmost1(a, 2));
        assertEquals(4, binarySearchRightmost1(a, 4));
        assertEquals(5, binarySearchRightmost1(a, 5));
        assertEquals(6, binarySearchRightmost1(a, 6));
        assertEquals(7, binarySearchRightmost1(a, 7));

        assertEquals(-1, binarySearchRightmost1(a, 0));
        assertEquals(-1, binarySearchRightmost1(a, 3));
        assertEquals(-1, binarySearchRightmost1(a, 8));
    }

    @Test
    @DisplayName("测试 binarySearchLeftmost 返回 i")
    public void test8() {
        int[] a = {1, 2, 4, 4, 4, 7, 8};
        assertEquals(0, binarySearchLeftmost2(a, 1));
        assertEquals(1, binarySearchLeftmost2(a, 2));
        assertEquals(2, binarySearchLeftmost2(a, 4));
        assertEquals(5, binarySearchLeftmost2(a, 7));
        assertEquals(6, binarySearchLeftmost2(a, 8));

        assertEquals(0, binarySearchLeftmost2(a, 0));
        assertEquals(2, binarySearchLeftmost2(a, 3));
        assertEquals(5, binarySearchLeftmost2(a, 5));
        assertEquals(7, binarySearchLeftmost2(a, 9));
    }

    @Test
    @DisplayName("测试 binarySearchRightmost 返回 i-1")
    public void test9() {
        int[] a = {1, 2, 4, 4, 4, 5, 6, 7};
        assertEquals(0, binarySearchRightmost2(a, 1));
        assertEquals(1, binarySearchRightmost2(a, 2));
        assertEquals(4, binarySearchRightmost2(a, 4));
        assertEquals(5, binarySearchRightmost2(a, 5));
        assertEquals(6, binarySearchRightmost2(a, 6));
        assertEquals(7, binarySearchRightmost2(a, 7));

        assertEquals(0, binarySearchRightmost2(a, 0) + 1);
        assertEquals(2, binarySearchRightmost2(a, 3) + 1);
        assertEquals(8, binarySearchRightmost2(a, 8) + 1);
    }

}
