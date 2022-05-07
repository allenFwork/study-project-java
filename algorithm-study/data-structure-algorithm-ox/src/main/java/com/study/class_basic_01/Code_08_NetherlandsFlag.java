package com.study.class_basic_01;

/**
 * 问题：荷兰国旗
 * 描述：
 * -- 给定一个数组arr，和一个数num，请把小于num的数放在数组的左边，
 * -- 等于num的数放在数组的中间，大于num的数放在数组的右边。
 * 要求：
 * -- 额外空间复杂度O(1)，时间复杂度O(N)
 */
public class Code_08_NetherlandsFlag {


    /**
     * 思路：
     * * 将数组分为三个区域，最左边是小于number的数，中间是等于number的数，右边是大于number的数
	 * * 小于number的区域起始位置设置为 p1 = left - 1
	 * * 大于number的区域起始位置设置为 p2 = right + 1
	 * * 当前位置 p = left
	 * * 从所给区域的第一个数值开始，如果小于number，就将这个数放到小于区域 p1 + 1, p + 1
	 * * 接着往右走，如果等于number，小于区域和大于区域不变，p + 1 (当前位置向右走一个)
	 * * 接着往右走，如果大于number，大于区域向左边扩展一个，p2 - 1, 变更将此时这个值放到那个扩展位置上，将扩展位置的数值放到此时的位置上，接着比较此时位置的值与number的大小
	 * * 。。。
     *
     * @param arr
     * @param left   表示数组中进行荷兰国旗逻辑的开始位置
     * @param right  表示数组中进行荷兰国旗逻辑的结束位置
     * @param number 具体比较的数值
     * @return
     */
    public static int[] partition(int[] arr, int left, int right, int number) {
        int lessIndex = left - 1;
        int moreIndex = right + 1;
        int currentIndex = left;
        while (currentIndex < moreIndex) {
            if (arr[currentIndex] < number) {
                swap(arr, ++lessIndex, currentIndex++);
            } else if (arr[currentIndex] > number) {
                swap(arr, --moreIndex, currentIndex);
            } else {
                currentIndex++;
            }
        }
        // 返回等于区域的下标: 左边界与有边界的下标
        return new int[]{lessIndex + 1, moreIndex - 1};
    }

    // 交换数组下标为 i 和 j 位置的数值
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for test
    public static int[] generateArray() {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 3);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] test = generateArray();
        printArray(test);
        int[] res = partition(test, 0, test.length - 1, 1);
        printArray(test);
        System.out.println(res[0]);
        System.out.println(res[1]);
    }
}
