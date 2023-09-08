package com.study.common_api;

public class MathDemo {

    // Math比较基础的方法使用
    public static void basicMethods() {
        // public static int abs(int a)         返回参数的绝对值
        System.out.println("-2的绝对值为：" + Math.abs(-2));
        System.out.println("2的绝对值为：" + Math.abs(2));

        // public static double ceil(double a)  返回大于或等于参数的最小整数
        System.out.println("大于或等于23.45的最小整数位：" + Math.ceil(23.45));
        System.out.println("大于或等于-23.45的最小整数位：" + Math.ceil(-23.45));

        // public static double floor(double a) 返回小于或等于参数的最大整数
        System.out.println("小于或等于23.45的最大整数位：" + Math.floor(23.45));
        System.out.println("小于或等于-23.45的最大整数位：" + Math.floor(-23.45));

        // public static int round(float a)     按照四舍五入返回最接近参数的int
        System.out.println("23.45四舍五入的结果为：" + Math.round(23.45));
        System.out.println("23.55四舍五入的结果为：" + Math.round(23.55));

        // public static int max(int a,int b)   返回两个int值中的较大值
        System.out.println("23和45的最大值为: " + Math.max(23, 45));

        // public static int min(int a,int b)   返回两个int值中的较小值
        System.out.println("12和34的最小值为: " + Math.min(12, 34));

        // public static double pow (double a,double b)返回a的b次幂的值
        System.out.println("2的3次幂计算结果为: " + Math.pow(2, 3));

        // public static double random()返回值为double的正值，[0.0,1.0)
        System.out.println("获取到的0-1之间的随机数为: " + Math.random());

        // public static double sqrt(double a): 返回正确舍入的 double 值的正平方根
        System.out.println("4的平方根为：" + Math.sqrt(4));
        System.out.println("10的平方根为：" + Math.sqrt(10.0));
    }

    /**
     * 统计一共有多少个水仙花数
     * 单词：
     * 1.daffodils：水仙花
     * 2.
     */
    public static int countTheDaffodils() {
        int count = 0;
        for (int i = 100; i <= 999; i++) {
            // 个位
            int unitsPath = i % 10;
            // 十位
            int decadePath = i / 10 % 10;
            // 百位
            int hundredsPath = i / 100 % 10;
            // int temp = unitsPath * unitsPath * unitsPath + decadePath * decadePath * decadePath + hundredsPath * hundredsPath * hundredsPath;
            double temp = Math.pow(unitsPath, 3) + Math.pow(decadePath, 3) + Math.pow(hundredsPath, 3); // 上一行注释掉的代码
            if (i == temp) { // int型 和 double型，只要数值相等，就返回true
                System.out.println("水仙花数：" + i);
                count++;
            }
        }
        return count;
    }

    /**
     * 统计有多少个四叶玫瑰数
     */
    public static int countTheRoseNumber() {
        int count = 0;
        for (int i = 1000; i <= 9999; i++) {
            // 个位
            int unitsPath = i % 10;
            // 十位
            int decadePath = i / 10 % 10;
            // 百位
            int hundredsPath = i / 100 % 10;
            // 千位
            int thousand = i / 1000 % 10;
            double temp = Math.pow(unitsPath, 4) + Math.pow(decadePath, 4) + Math.pow(hundredsPath, 4) + Math.pow(thousand, 4);
            if (i == temp) { // int型 和 double型，只要数值相等，就返回true
                System.out.println("四叶玫瑰数：" + i);
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        basicMethods();
        countTheDaffodils();
        countTheRoseNumber();
    }

}
