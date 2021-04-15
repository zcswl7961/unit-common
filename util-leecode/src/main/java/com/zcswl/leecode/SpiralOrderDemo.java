package com.zcswl.leecode;

import java.util.ArrayList;
import java.util.List;

/**
 * 二维数组
 * 螺旋矩阵
 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
 * @author zhoucg
 * @date 2021-04-15 11:30
 */
public class SpiralOrderDemo {


    public static void main(String[] args) {

    }


    private static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> order = new ArrayList<Integer>();

        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return order;
        }
        // 表示对应的行数
        int rows = matrix.length;
        // 表示对应的列数
        int columns = matrix[0].length;
        int left = 0, right = columns - 1, top = 0, bottom = rows - 1;
        while (left <= right && top <= bottom) {
            // 从左到右
            for (int column = left; column <= right; column++) {
                order.add(matrix[top][column]);
            }
            // 这个没问题嘛？
            for (int row = top + 1; row <= bottom; row++) {
                order.add(matrix[row][right]);
            }
            if (left < right && top < bottom) {
                for (int column = right - 1; column > left; column--) {
                    order.add(matrix[bottom][column]);
                }
                for (int row = bottom; row > top; row--) {
                    order.add(matrix[row][left]);
                }
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        return order;
    }

    /**
     * 第二种方法，进行上下左右递归旋转操作
     * left = 0 right = matrix[0].length;(列数)
     * up = 0 down = matrix.length (行数)
     * 00 01 02 03 04 05
     * 10 11 12 13 14 15
     * 20 21 22 23 24 25
     */
    private static List<Integer> spiralOrder1(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int count = 0, row = matrix.length, column = matrix[0].length;
        int total = row * column;
        int up = 0, down = row - 1, left = 0, right = column - 1;
        while (count < total) {
            // 00 01 02 03 04 05
            // 向右
            for (int i = left; i <= right && count < total; i++) {
                res.add(matrix[up][i]);
                count++;
            }
            up++;
            // 向下
            // 15 25
            for (int i = up; i<= down && count < total; i++) {
                res.add(matrix[i][right]);
                count++;
            }
            right--;

            // 向左
            for (int i = right; i >= left && count < total; i--) {
                res.add(matrix[down][i]);
                count++;
            }
            down--;

            // 向上
            for (int i = down; i >= up && count < total; i--) {
                res.add(matrix[i][left]);
                count++;
            }
            left++;
        }
        return res;
    }
}
