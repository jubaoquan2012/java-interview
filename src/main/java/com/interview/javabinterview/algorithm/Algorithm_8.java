package com.interview.javabinterview.a_algorithm;

/**
 * 数组去重
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_8 {

    public static void main(String[] args) {
        System.out.println("");
    }

    private static int removeDuplicates(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int slow = 0;
        for (int fast = 1; fast < array.length; fast++) {
            if (array[slow] != array[fast]) {
                slow++;
                array[slow] = array[fast];
            }
        }
        return slow + 1;
    }
}
