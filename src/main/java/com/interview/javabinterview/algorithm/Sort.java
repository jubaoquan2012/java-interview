package com.interview.javabinterview.algorithm;

public class Sort {

    public static void main(String[] args) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        /**插入排序*/
        // 直接插入排序
        // 希尔排序
        /**选择排序*/
        // 简单选择排序
        //selectionSort(array);
        // 堆排序
        /**交换排序*/
        // 冒泡排序
        // bubbleSort(array);
        // 快速排序
         quickSort(array, 0, array.length - 1);
        /**归并排序*/
        /**基数排序*/
        AlgorithmUtils.print(array);
    }

    /**
     * 冒泡排序
     */
    public static void bubbleSort(int[] array) {
        if (array.length == 0) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 快速排序(交换排序)
     */
    private static void quickSort(int[] array, int low, int high) {
        if (array == null || array.length == 0) {
            return;
        }
        if (low >= high) {
            return;
        }

        int pivot = array[low];
        int left = low, right = high;
        while (left < right) {
            while (left < right && array[right] >= pivot) {
                right--;
            }
            while (left < right && array[left] <= pivot) {
                left++;
            }
            if (left < right) {
                int t = array[left];
                array[left] = array[right];
                array[right] = t;
            }
        }

        array[low] = array[left];
        array[left] = pivot;

        quickSort(array, left + 1, high);
        quickSort(array, low, left - 1);
    }

    /**
     * 简单选择排序
     */
    private static void selectionSort(int[] array) {
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
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }
}
