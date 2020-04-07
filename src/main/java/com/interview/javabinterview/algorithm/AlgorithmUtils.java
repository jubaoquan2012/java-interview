package com.interview.javabinterview.algorithm;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/7
 */
public class AlgorithmUtils {

    public static void print(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        System.out.println(sb.toString());
    }

    /**
     * 两个位置交换
     *
     * @param array
     * @param a
     * @param b
     */
    public static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * 当前位置和下一个位置交换
     */
    public static void swapNext(int[] array, int currentIndex) {
        int temp = array[currentIndex];
        array[currentIndex] = array[currentIndex + 1];
        array[currentIndex + 1] = temp;
    }

    public static int[] reverse(int[] array) {
        int minIndex = 0;
        int maxIndex = array.length - 1;
        while (minIndex < maxIndex) {
            int temp = array[minIndex];
            array[maxIndex] = array[maxIndex];
            array[minIndex] = temp;
            minIndex++;
            maxIndex--;
        }
        return array;
    }
}
