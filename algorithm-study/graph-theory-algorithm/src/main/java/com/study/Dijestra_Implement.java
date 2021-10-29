package com.study;

import java.util.Scanner;

// 迪杰斯特拉算法：贪心策略 实现
public class Dijestra_Implement {

    /**
     * 贪心算法寻找最短路径
     *
     * @param n           有多少个点
     * @param x           起始位置对应的点
     * @param destination 最短距离存储数组
     * @param weightValue 各点相互之间的权重值
     */
    public static void searchShortestDistance(int n, int x, int[] destination, int[][] weightValue) {
        // 确定到某个点的最短距离后，就该点从计算路径的点中排除掉，这是该点是否确定的标识的存储数组
        boolean[] marks = new boolean[n + 1];
        // 初始化点的标识
        for (int i = 0; i < n; i++) {
            marks[i] = false;
        }
        // 起始位置到临近位置的最短距离已经计算过了,并存储到destination数组中了
        marks[x] = true;
        // 自己到自己的最短距离为0
        destination[x] = 0;

        // 贪心算法逻辑
        int count = 1;
        while (count <= n) {
            // 表示起点到哪个点的距离最短
            int location = 0;
            int minDistance = Integer.MAX_VALUE;
            // 找到起点到哪个点的距离最短
            for (int i = 1; i < n; i++) {
                if (!marks[i] && destination[i] < minDistance) {
                    location = i;
                    minDistance = destination[i];
                }
            }

            if (location == 0) break;
            marks[location] = true;
            count++;

            //
            for (int i = 1; i < n; i++) {
                if (!marks[i] && weightValue[location][i] != -1 && (destination[location] + weightValue[location][i]) < destination[i]) {
                    destination[i] = destination[location] + weightValue[location][i];
                }
            }

            // 打印结果：
            System.out.println("以" + x + "为起点的最短路程：");
            for (int i = 1; i < n; i++) {
                if (destination[i] != Integer.MAX_VALUE) {
                    System.out.println(i + "最短为：" + destination[i]);
                } else {
                    System.out.println(i + "没有路");
                }

            }

        }
    }

    public static void main(String[] args) {
        // n, m, x: n表示n个点，m条边, x表示要求的最短路径的起始位置
        int n, m, x;
        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入位置的个数：");
        n = scanner.nextInt();
//        System.out.println("请输入边数（路径）：");
        m = scanner.nextInt();
//        System.out.println("请输入出发的位置");
        x = scanner.nextInt();

        // 用于存储点到点之间的权重值（距离，价值大小）
        int weight[][] = new int[n + 1][n + 1];
        // 用于存储计算出的结果：最短路径值（最小的权重值相加路径），有n个点，所以从1个点能到n个点种情况
        int[] destination = new int[n + 1];

        // 初始化权重（路径）数据
        for (int i = 1; i <= n; i++) {
            // 初始化最短路径数据,默认到每个点的距离全是无穷大
            destination[i] = Integer.MAX_VALUE;
            for (int j = 1; j <= n; j++) {
                // 自己到自己的边（路径，权重值）为 0
                if (i == j)
                    weight[i][j] = 0;
                    // 除了自己到自己的路, 其余的边的权重值都设为-1, 默认为无法到达
                else
                    weight[i][j] = -1;
            }
        }

        // 设置实际的权重数据(除了自己到自己，实际能够到达两点权重)
        for (int i = 0; i < m; i++) {
            int startLocation = scanner.nextInt();
            int endLocation = scanner.nextInt();
            int weightVale = scanner.nextInt();
            weight[startLocation][endLocation] = weightVale;
            // 如果是起始位置,那么此时最短路径就是到endLocation的值
            if (startLocation == x) {
                destination[endLocation] = weightVale;
            }
        }

        // 执行 迪杰斯特拉算法：贪心策略 寻找最短路径
        searchShortestDistance(n, x, destination, weight);

    }

}
