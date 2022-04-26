package com.study.first_chapter.content;

/**
 * 算法题：从N个数中选出其中第K大的数
 */
public class Demo1 {

    public static void main(String[] args) {
        // 创建数组是需设置容量大小
        int[] arr = new int[15];
        arr[0] = 11;
        arr[1] = 2;
        arr[2] = 3;
        arr[3] = 4;
        arr[4] = 5;
        arr[5] = 6;
        arr[6] = 7;
        arr[6] = 1;
        arr[7] = 9;
        arr[8] = 8;
        arr[9] = 9;
        arr[10] = 10;
        System.out.println("排序前：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
        System.out.println("排序后：");
        int[] arr2 = bubbleSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr2[i] + " ");
        }

    }


    /**
     * 冒泡排序
     */
    public static int[] bubbleSort(int[] sourceArray) {
        // 遍历
        for (int i = sourceArray.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                int item = sourceArray[j];
                int itemRight = sourceArray[j + 1];
                int temp;
                if (item > itemRight) {
                    temp = item;
                    sourceArray[j] = itemRight;
                    sourceArray[j + 1] = temp;
                }
            }
        }
        return sourceArray;
    }

    /**
     * 思路一：通过冒泡排序，然后获取第K个位置的数值
     */
    public static int getNumber(int[] sourceArray, int K) {
        int[] sortedArray = bubbleSort(sourceArray);
        return sortedArray[K - 1];
    }


    /**
     * 思路二：存储前K个数据,然后
     */
    public static int getNumber2(int[] sourceArray, int K) {
        return 0;
    }

}


