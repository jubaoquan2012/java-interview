package com.interview.javabinterview.algorithm;

public class Sort1 {

    public static void main(String[] args) {
        int[] array = {
                6000001,
                4000019,
                4000007,
                4000022,
                4000023,
                100000217,
                4000025,
                100000202
        };
        fastSort(array,0,array.length-1);
        AlgorithmUtils.print(array);
    }

    /**
     * 冒泡排序
     * <p>
     * step1:比较相邻的元素。如果第一个比第二个大，就交换它们两个
     *
     * @return array
     */
    public static int[] bubbleSort(int[] array) {
        if (array.length == 0) {
            return array;
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j + 1] < array[j]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    public static void fastSort(int[] array, int low, int high) {
        if (array.length == 0) {
            return;
        }
        if (low >= high) {
            return;
        }

        int pivot = array[low];
        int left =low, right = high;

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
        array[low] = array[left];
        array[left] = pivot;

        fastSort(array, left + 1, high);

        fastSort(array, low, left - 1);
    }
}
