package com.study.jvm.demo1.interview;

public class Exercise1 {

    /** 字节码指令
         0 iconst_0
         1 istore_1
         2 iconst_0
         3 istore_2
         4 iconst_0
         5 istore_3
         6 iinc 1 by 1
         9 iload_2
        10 iconst_1
        11 iadd
        12 istore_2
        13 iinc 3 by 1
        16 return
     */
    public static void main(String[] args) {
        int i = 0, j = 0, k = 0;
        i++;
        j = j + 1;
        k += 1;
    }

}
