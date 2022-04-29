package com.study.class_basic_03;

/**
 * 转圈打印矩阵
 * 描述：给定一个整型矩阵matrix，请按照转圈的方式打印它。
 * 例如：1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 打印结果为：1，2，3，4，8，12，16，15，14，13，9，5，6，7，11，10
 * 要求：额外空间复杂度为O(1)
 */
public class Code_06_PrintMatrixSpiralOrder {

	/**
	 * 思路：
	 * 1.打印最外面的框，接着打印向内的第一个框，一次推
	 * 2.条件：左上角的行小于等于右小角的行 且 左上角的列小于等于右小角的列
	 */
    public static void spiralOrderPrint(int[][] matrix) {
        int tRow = 0;
        int tCol = 0;
        int dRow = matrix.length - 1;
        int dCol = matrix[0].length - 1;
        while (tRow <= dRow && tCol <= dCol) {
            printEdge(matrix, tRow++, tCol++, dRow--, dCol--);
        }
    }

    /**
     * 打印所给左上角和右下角两个点所对应的长方形矩阵边框,按照逆时针方向打印
     *
     * @param m    二阶矩阵,二维数组
     * @param tRow 左上角的行
     * @param tCol 左上角的列
     * @param dRow 右下角的行
     * @param dCol 右下角的列
     */
    public static void printEdge(int[][] m, int tRow, int tCol, int dRow, int dCol) {
        if (tRow == dRow) {
            for (int i = tCol; i <= dCol; i++) {
                System.out.print(m[tRow][i] + " ");
            }
        } else if (tCol == dCol) {
            for (int i = tRow; i <= dRow; i++) {
                System.out.print(m[i][tCol] + " ");
            }
        } else {
            int currentRow = tRow;
            int currentCol = tCol;
            while (currentCol != dCol) {
                System.out.print(m[tRow][currentCol] + " ");
                currentCol++;
            }
            while (currentRow != dRow) {
                System.out.print(m[currentRow][dCol] + " ");
                currentRow++;
            }
            while (currentCol != tCol) {
                System.out.print(m[dRow][currentCol] + " ");
                currentCol--;
            }
            while (currentRow != tRow) {
                System.out.print(m[currentRow][tCol] + " ");
                currentRow--;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        spiralOrderPrint(matrix);
    }

}
