package com.study;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 广度优先算法
 */
public class BFS {

    public static int searchPathNumber() {
        return 0;
    }

    public static void main(String[] args) {
        // n 表示位置(节点)的个数, m 表示边数(路径个数)
        int n, m;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        // 存储能够走通的路径
        int paths[][] = new int[n + 1][n + 1];
        // 初始化能够走通的路径
        for (int i = 0; i < m; i++) {
            // 起始位置(起始节点值)
            int startLocation = scanner.nextInt();
            // 终点位置(终止节点值)
            int endLocation = scanner.nextInt();
            // 表示从 startLocation 到 endLocation 能够走得通，为0表示走不通
            paths[startLocation][endLocation] = 1;
        }

        //
        Queue<Integer> queue = new LinkedBlockingQueue();
        // 要计算路径的起始位置
        int start = scanner.nextInt();
        // 要计算路径的终点位置
        int end = scanner.nextInt();

        queue.add(start);
        // 总共能够走通的路径条数
        int pathNumber = 0;
        while (!queue.isEmpty()) {
            int currentLocation = queue.poll();
            for (int i=1; i<=n; i++) {
                if (paths[currentLocation][i] == 1) {
                    if (i == end) {
                        pathNumber++;
                    } else {
                        queue.add(i);
                    }
                }
            }
        }

    }
}
