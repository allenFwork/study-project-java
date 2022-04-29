package com.study.class_basic_03;

/**
 * 旋转正方形矩阵
 * 描述：给定一个整型正方形矩阵matrix，请把该矩阵调整成顺时针旋转90度的样子。
 * 要求：额外空间复杂度为O(1)。
 */
public class Code_05_RotateMatrix {

    public static void rotate(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR < dR) {
            rotateEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    /**
     * @param matrix 正方形矩阵,二维数组
     * @param tRow   左上角的行
     * @param tCol   左上角的列
     * @param dRow   右上角的行
     * @param dCol   右上角的列
     */
    public static void rotateEdge(int[][] matrix, int tRow, int tCol, int dRow, int dCol) {
        // 触发点的个数
        int times = dCol - tCol;
        int tmp = 0;
        /**
         * 找到触发点,并找到该出发点对应的三个点,进行交换位置
         */
        for (int i = 0; i != times; i++) {
            tmp = matrix[tRow][tCol + i];
            matrix[tRow][tCol + i] = matrix[dRow - i][tCol];
            matrix[dRow - i][tCol] = matrix[dRow][dCol - i];
            matrix[dRow][dCol - i] = matrix[tRow + i][dCol];
            matrix[tRow + i][dCol] = tmp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);

    }

}
