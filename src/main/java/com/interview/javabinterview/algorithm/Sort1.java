package com.interview.javabinterview.algorithm;


public class Sort1 {

    public static void main(String[] args) {
        int[] array = {26, 45, 59, 14, 21, 28, 17, 18, 30};
        changeSort(array, 4);
    }

    private static void changeSort(int[] array, int index) {
        for (int i = 0; i < array.length - index - 1; i++) {
            int left = index + i;
            while (left >= i) {
                int temp = array[left + 1];
                array[left + 1] = array[left];
                array[left] = temp;
                left--;
            }
        }

        int left = array.length;
        while (left > array.length - index) {
            int temp = array[left - 1];
            array[left - 1] = array[left-2];
            array[left-2] = temp;
            left--;
        }
    }
}
