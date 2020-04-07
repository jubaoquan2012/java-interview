package com.interview.javabinterview.algorithm.byte_dance;

import com.alibaba.fastjson.JSON;

/**
 * 生命游戏
 * <p>
 * 根据百度百科，生命游戏，简称为生命，是英国数学家约翰·何顿·康威在1970年发明的细胞自动机。
 * <p>
 * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞具有一个初始状态 live（1）即为活细胞， 或 dead（0）即为死细胞。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
 * <p>
 * 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
 * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
 * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
 * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
 * <p>
 * <p>
 * 根据当前状态，写一个函数来计算面板上细胞的下一个（一次更新后的）状态。
 * 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，
 * 其中细胞的出生和死亡是同时发生的。
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class GameOfLife {

    public static void main(String[] args) {
        int[][] board = {
                {0, 1, 0},
                {0, 0, 1},
                {1, 1, 1},
                {0, 0, 0}
        };

        //输出结果::
        int[][] result = {
                {0, 0, 0},
                {1, 0, 1},
                {0, 1, 1},
                {0, 1, 0}
        };

        int[][] arrays = gameOfLife(board);
        System.out.println(JSON.toJSONString(arrays));
    }

    /**
     * 生命游戏
     *
     * @param board Cell 矩阵
     * @return 细胞最后结果
     */
    private static int[][] gameOfLife(int[][] board) {
        //1. 创建一个二维数组来存储给定的board数组每个一细胞将要改变的状态
        int row = board.length;         //4行 对应 y
        int column = board[0].length;   //3列 对应 x

        int[][] arrays = new int[row][column];
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < column; x++) {
                int count = findLiveCellCount(y, x, row, column, board);
                //如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
                if (count < 2) {
                    arrays[y][x] = 0;
                } else if (count > 3) {
                    //如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
                    //todo 活细胞仍然存活,啥都不做
                    //如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
                    arrays[y][x] = 0;
                } else if (count == 3 && arrays[y][x] == 0) {
                    //如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
                    arrays[y][x] = 1;
                } else {
                    arrays[y][x] = board[y][x];
                }
            }
        }

        for (int x = 0; x < row; x++) {
            //            for (int y = 0; y < column; y++) {
            //                board[x][y] = arrays[x][y];
            //            }
            System.arraycopy(arrays[x], 0, board[x], 0, column);
        }

        return board;
    }

    /**
     * 统计 [x,y]细胞周围活细胞数量
     *
     * @param x x坐标  column
     * @param y y坐标  row
     * @param row 行
     * @param column 列
     * @param board 面板
     * @return 周围活细胞数量
     */
    private static int findLiveCellCount(int y, int x, int row, int column, int[][] board) {
        int liveCellCount = 0;
        if (x >= 0 && y >= 0 && y < row && x < column) {
            //(x-1)
            if (x - 1 >= 0) {
                //(x-1,y-1)
                if (y > 0 && board[y - 1][x - 1] == 1) {
                    liveCellCount++;
                }
                //(x-1,y)
                if (board[y][x - 1] == 1) {
                    liveCellCount++;
                }
                //(x-1,y+1)
                if (y + 1 < row && board[y + 1][x - 1] == 1) {
                    liveCellCount++;
                }
            }

            //(x)
            //(x,y-1)
            if (y > 0 && board[y - 1][x] == 1) {
                liveCellCount++;
            }

            //(x,y+1)
            if (y + 1 < row && board[y + 1][x] == 1) {
                liveCellCount++;
            }

            //(x+1)
            if (x + 1 < column) {
                //(x+1,y-1)
                if (y > 0 && board[y - 1][x + 1] == 1) {
                    liveCellCount++;
                }
                //(x+1,y)
                if (board[y][x + 1] == 1) {
                    liveCellCount++;
                }
                //(x+1,y+1)
                if (y + 1 < row && board[y + 1][x + 1] == 1) {
                    liveCellCount++;
                }
            }
        }
        return liveCellCount;
    }
}
