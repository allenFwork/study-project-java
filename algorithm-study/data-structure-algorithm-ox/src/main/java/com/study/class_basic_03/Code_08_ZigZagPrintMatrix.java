package com.study.class_basic_03;

/**
 * “之”字形打印矩阵
 * 描述：给定一个矩阵matrix，按照“之”字形的方式打印这个矩阵，
 * 例如：1 2 3 4 5 6 7 8 9 10 11 12 “之”字形打印的结果为：1，2，5，9，6，3，4，7，10，11，8，12
 * 要求：额外空间复杂度为O(1)
 */
public class Code_08_ZigZagPrintMatrix {

    public static void printMatrixZigZag(int[][] matrix) {
        // 右上角的行
        int tRow = 0;
        // 右上角的列
        int tCol = 0;
        // 左下角的行
        int dRow = 0;
        // 左下角的列
        int dCol = 0;
        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;
        // 打印对角线数据的顺序: true表示从上往下,false表示从下往上
        boolean fromUp = false;
        while (tRow != endRow + 1) {
            printLevel(matrix, tRow, tCol, dRow, dCol, fromUp);
            tRow = tCol == endCol ? tRow + 1 : tRow;
            tCol = tCol == endCol ? tCol : tCol + 1;
            dCol = dRow == endRow ? dCol + 1 : dCol;
            dRow = dRow == endRow ? dRow : dRow + 1;
            // “之”字形打印时,每次打印完一条斜线,方向都会发生改变
            fromUp = !fromUp;
        }
        System.out.println();
    }

    public static void printLevel(int[][] m, int tR, int tC, int dR, int dC, boolean f) {
        if (f) {
            while (tR != dR + 1) {
                System.out.print(m[tR++][tC--] + " ");
            }
        } else {
            while (dR != tR - 1) {
                System.out.print(m[dR--][dC++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        printMatrixZigZag(matrix);

    }

}
