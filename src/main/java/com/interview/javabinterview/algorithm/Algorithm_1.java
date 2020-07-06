package com.interview.javabinterview.a_algorithm;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * 排序
 *
 * @author Ju Baoquan
 * Created at  2020/5/11
 */
public class Algorithm_1 {

    public static void main(String[] args) {
        int[] array = Algorithm_Base.array;
        int[] arraySort = Algorithm_Base.arraySort;

        //bubbleSort(array);
        //quickSort(array, 0, array.length - 1);
        insertSort(array);
        //selectSort(array);
        System.out.println(JSON.toJSONString(array));
    }

    /**
     * 冒泡排序
     */
    private static int[] bubbleSort(int[] array) {
        if (array.length == 0) {
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

    /**
     * 快速排序
     */
    private static void quickSort(int[] array, int low, int high) {
        if (array.length == 0 || low >= high) {
            return;
        }
        int pivot = array[low];
        int left = low, right = high;
        //1.分治
        while (left < right) {
            while (left < right && array[right] >= pivot) {
                right--;
            }
            while (left < right && array[left] <= pivot) {
                left++;
            }
            if (left < right) {
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;
            }
        }
        //2.把分治数放到中间(非length/2)位置
        array[low] = array[left];
        array[left] = pivot;
        quickSort(array, low, left - 1);
        quickSort(array, left + 1, high);
    }

    /**
     * 选择排序: 其实就是选择最小的
     * 每次找出最小的数, 然后依次插入到前面
     */
    private static void selectSort(int[] array) {
        if (array.length == 0) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    /**
     * 3. 直接插入排序
     * 从第二个数(current)开始, 如果current小于前面的数, 需要把current插入到前面自然有序的位置:
     * 例如: 13, 45, 59, 14, 18, 21, 17, 18
     * 假设 current = 14 插入后:
     * **** 13, 14, 45, 59, 18, 21, 17, 18
     *
     * @param array
     * @return
     */
    private static int[] insertSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        for (int i = 0; i < array.length - 1; i++) {
            int current = array[i + 1];
            int pre = i;
            while (pre >= 0 && current < array[pre]) {
                //需要把pre之前的每个数后移一位
                array[pre + 1] = array[pre];
                pre--;
            }
            array[pre + 1] = current;
        }
        return array;
    }

    /**
     * 希尔排序就是插入排序的一种
     */
    private int[] shellSort(int[] array) {
        int len = array.length;
        int temp, gap = len / 2;
        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                temp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && temp < array[preIndex]) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;
            }
            gap /= 2;
        }
        return array;
    }

    /**
     * 归并排序
     */
    private static Integer[] mergeSort(Integer[] arr) {
        if (arr.length < 2) {
            return arr;
        }
        int mid = arr.length / 2;
        Integer[] left = Arrays.copyOfRange(arr, 0, mid);
        Integer[] right = Arrays.copyOfRange(arr, mid, arr.length);
        return mergeSort(mergeSort(left), mergeSort(right));
    }

    private static Integer[] mergeSort(Integer[] left, Integer[] right) {
        Integer[] integers = new Integer[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < integers.length; index++) {
            if (i >= left.length) {
                integers[index] = right[j++];
            } else if (j >= right.length) {
                integers[index] = left[i++];
            } else if (left[i] > right[j]) {
                integers[index] = right[j++];
            } else {
                integers[index] = left[i++];
            }
        }
        return integers;
    }


    /**
     * 奇偶排序
     * 思路:
     * 例如: [6 2 4 1 5 9]
     * 第一次: 比较奇数列 6:2比,4:1比,5:9比 :[2 6 1 4 5 9]
     * 第二次: 比较偶数列 6:1,4:5      :[2 1 6 4 5 9]
     * 第三次: 比较奇数列 2:1,6:4,5:9  :[1 2 4 6 5 9]
     */
    private static int[] oddSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        boolean exchangeFlag = true;
        int start = 0;
        while (exchangeFlag || start == 1) {
            exchangeFlag = false;
            for (int i = start; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    AlgorithmUtils.swapNext(array, i);
                    exchangeFlag = true;
                }
            }
            if (start == 0) {
                start = 1;
            } else {
                start = 0;
            }
        }
        return array;
    }

    /**
     * 给定一个数组，数组中有正数和负数
     * 1.正负数交替排列
     * 2.保证各自的相对顺序不变
     */
    private static int[] pnSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int slow = 0;
        while (slow < array.length - 1) {
            if (array[slow] * array[slow + 1] > 0) {
                int index = 0;
                for (int fast = slow + 1; fast < array.length; fast++) {
                    if (array[slow] * array[fast] < 0) {
                        index = fast;
                        break;
                    }
                }
                int temp = array[index];
                for (int i = index; i >= slow + 1; i--) {
                    array[i] = array[i - 1];
                }
                array[slow + 1] = temp;
            }
            slow++;
        }
        return array;
    }

}
