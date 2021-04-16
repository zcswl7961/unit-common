package com.zcswl.leecode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 岛屿数量
 * dfs算法
 * @author zhoucg
 * @date 2021-04-15 18:11
 */
public class 岛屿数量 {


    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1}
        };


    }


    /**
     * 图的深度优先遍历（DFS）和广度优先遍历（BFS）
     * https://www.jianshu.com/p/bff70b786bb6
     * https://segmentfault.com/a/1190000002685939
     *
     *
     */

    public static int numsIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0 ;
        }
        int row = grid.length;
        int columns = grid[0].length;

        //设置一个全局的值
        int nums_lsland = 0;
        for (int r = 0; r < row; r++) {
            for (int t = 0 ;t < columns; t++) {
                if (grid[r][t] == '1') {
                    nums_lsland++;
                    dfs(grid, r, t);
                }
            }
        }
        return nums_lsland;
    }

    public static void dfs(char[][] grid, int r, int t) {
        int nr = grid.length;
        int nc = grid[0].length;

        // grid[r][t] == '0' 这个为什么要加
        if (r < 0 || t < 0 || r >= nr || t >= nc || grid[r][t] == '0') {
            return;
        }
        grid[r][t] = '0';
        // 想前一个
        dfs(grid, r-1, t);
        dfs(grid, r+1, t);
        dfs(grid, r,t-1);
        dfs(grid, r, t+1);



    }

    /**
     * 广度优先搜索
     * 0 0 0 0 0
     * 0 1 0 0 0
     * 0 0 0 0 1
     */
    public static int numsIslands1(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int nr = grid.length; // row = 3
        int nc = grid[0].length; // columns = 5
        int num_islands = 0;
        for (int r = 0; r < nr; r++) {
            for (int c = 0; c < nc; c++) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    grid[r][c] = '0';
                    // 使用队列的方式
                    Queue<Integer> neighbors = new LinkedList<>();
                    // r 表示当前的位置，1 nc = 5 + 1
                    neighbors.add(r * nc + c);
                    while (!neighbors.isEmpty()) {
                        int id = neighbors.remove();
                        int row = id /nc; // 6 / 5 == 1
                        int col = id %nc;// 6 % 5 == 1
                        // 找到对应的邻居信息,上邻居
                        if (row - 1 >= 0 && grid[row-1][col] == '1') {
                            neighbors.add((row-1) * nc + col);
                            grid[row-1][col] = '0';
                        }
                        // 找到对应的邻居，下面的操作
                        if (row +1 < nr && grid[row+1][col] == '1') {
                            neighbors.add((row+1) * nc + col);
                            grid[row+1][col] = '0';
                        }

                        // 找到对应的邻居，左边的操作
                        if (col - 1 >= 0 && grid[row][col-1] == '1') {
                            neighbors.add(row * nc + col - 1);
                            grid[row][col-1] = '0';
                        }
                        // 找到对应的另据，右边的操作
                        if (col + 1 < nc && grid[row][col+1] == '1') {
                            neighbors.add(row * nc + col + 1);
                            grid[row][col+1] = '0';
                        }

                    }
                }
            }
        }
        return num_islands;
    }
}
