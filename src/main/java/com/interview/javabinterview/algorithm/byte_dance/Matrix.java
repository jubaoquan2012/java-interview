package com.interview.javabinterview.algorithm.byte_dance;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 矩阵算法
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class Matrix {

    public static void main(String[] args) {
        // 初始化矩阵,假设此矩阵为 5 x 5 矩阵
        int[] matrix1 = {
                0, 0, 0, 0, 1,
                0, 0, 1, 1, 1,
                0, 0, 0, 1, 0,
                0, 0, 0, 0, 0,
        };
        int max = Matrix.getMaxSeqSum(matrix1, 5);
        System.out.println(max);  // 打印4

        int[] matrix2 = {
                0, 0, 0, 0, 1,
                0, 0, 1, 1, 1,
                0, 0, 1, 1, 0,
                0, 0, 0, 0, 0,
        };

        int max2 = Matrix.getMaxSeqSum(matrix2, 5);
        System.out.println(max2);  // 打印4
    }

    /**
     * 获取矩阵序列最大和
     *
     * @param matrix 矩阵
     * @param dimension dimension 阶矩阵
     * @return 最大序列和
     */
    public static int getMaxSeqSum(int[] matrix, int dimension) {
        if (matrix.length == 0) {
            return -1;
        }
        int count = matrix.length;
        int maxSeqSum = 0;
        for (int index = 0; index < count; index++) {
            int indexValue = matrix[index];
            if (indexValue == 1) {
                Set<Integer> traverseElementIndexSet = new HashSet<>();
                traverseElementIndexSet.add(index);
                int currentMaxSeqSum = getCurrentMaxSeqSum(matrix, dimension, traverseElementIndexSet, index);
                maxSeqSum = Math.max(currentMaxSeqSum, maxSeqSum);
            }
        }
        return maxSeqSum;
    }

    private static int getCurrentMaxSeqSum(int[] matrix, int dimension, Set<Integer> traverseElementIndexSet, int index) {

        int left = index - 1;
        int right = index + 1;
        int up = index - dimension;
        int down = index + dimension;

        int leftMaxSeqSum = 0;
        int rightMaxSeqSum = 0;
        int upMaxSeqSum = 0;
        int downMaxSeqSum = 0;

        if (left >= 0 && matrix[left] == 1 && !traverseElementIndexSet.contains(left)) {
            Set<Integer> newTraverElementIndexSet = new HashSet<>(traverseElementIndexSet);
            newTraverElementIndexSet.add(left);
            leftMaxSeqSum = getCurrentMaxSeqSum(matrix, dimension, newTraverElementIndexSet, left);
        } else {
            leftMaxSeqSum = traverseElementIndexSet.size();
        }

        if (right / dimension == index / dimension && matrix[right] == 1 && !traverseElementIndexSet.contains(right)) {
            Set<Integer> newTraverElementIndexSet = new HashSet<>(traverseElementIndexSet);
            newTraverElementIndexSet.add(right);
            rightMaxSeqSum = getCurrentMaxSeqSum(matrix, dimension, newTraverElementIndexSet, right);
        } else {
            rightMaxSeqSum = traverseElementIndexSet.size();
        }

        if (up >= 0 && matrix[up] == 1 && !traverseElementIndexSet.contains(up)) {
            Set<Integer> newTraverElementIndexSet = new HashSet<>(traverseElementIndexSet);
            newTraverElementIndexSet.add(up);
            upMaxSeqSum = getCurrentMaxSeqSum(matrix, dimension, newTraverElementIndexSet, up);
        } else {
            upMaxSeqSum = traverseElementIndexSet.size();
        }

        if (down < matrix.length  && matrix[down] == 1 && !traverseElementIndexSet.contains(down)) {
            Set<Integer> newTraverElementIndexSet = new HashSet<>(traverseElementIndexSet);
            newTraverElementIndexSet.add(up);
            downMaxSeqSum = getCurrentMaxSeqSum(matrix, dimension, newTraverElementIndexSet, down);
        } else {
            downMaxSeqSum = traverseElementIndexSet.size();
        }

        return Collections.max(Arrays.asList(leftMaxSeqSum, rightMaxSeqSum, upMaxSeqSum, downMaxSeqSum));
    }
}
