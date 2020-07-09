package com.interview.javabinterview.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_ {

    public static void main(String[] args) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};

        //bubbleSort(array);
        quickSort(array, 0, array.length - 1);
        System.out.println(JSON.toJSONString(array));
    }

    /**
     * 快速排序: 比较排序: 设置一个分治点, 通过比较和分治点的数比较,大的数和小的数分为两边, 递归执行.
     *
     * @param array
     * @return
     */
    private static void quickSort(int[] array, int low, int high) {
        if (array == null || array.length == 0 || low > high) {
            return;
        }
        int pivot = array[low];
        int left = low;
        int right = high;
        while (left < right) {
            while (left < right && array[right] >= pivot) {
                right--;
            }
            while (left < right && array[left] <= pivot) {
                left++;
            }
            if (left < right) {
                int temp = array[right];
                array[right] = array[left];
                array[left] = temp;
            }
        }
        //把分治点的数放到中间位置
        array[low] = array[left];
        array[left] = pivot;
        quickSort(array, low, left - 1);
        quickSort(array, left + 1, high);
    }

    /**
     * 冒泡排序: 比较排序: 走访序列, 通过比较相邻的两个数,较大的数"浮"到后面.
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(1)
     */
    private static int[] bubbleSort(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }
}
