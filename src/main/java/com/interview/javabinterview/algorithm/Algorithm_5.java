package com.interview.javabinterview.a_algorithm;

/**
 * 输入一个数,重新排列组合,输出下一个大的数
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_5 {

    public static void main(String[] args) {
        System.out.println("");
    }

    /**
     * 1 5 8 4 7 6 5 3 1
     * step1:从右边开始,比较连续的两个数,找到第一组顺序的两个数          1  5  8  4_ 7  6  5  3  1
     * step2:从右向左,找到第一个 大于等于index(4)的数, 然后交换位置:    1  5  8  5_ 7  6  4_ 3  1
     * step3:index(4)后面的数字反转 (前两步已经保证index(4)后的顺序了)
     * 所以只需要反转就可以,不需要重新排序                            1  5  8  5  1  3  4  6  7
     */
    private static int[] nextPermutation(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int i = array.length - 2;
        /** 1.从右边开始,比较连续的两个数,找到第一组顺序的两个数:即  array[i] < array[i + 1]*/
        while (i >= 0 && array[i + 1] <= array[i]) {
            i--;
        }
        if (i >= 0) {
            int j = array.length - 1;
            /**step2.从右向左,找到第一个 大于等于index(4)的数, 然后交换位置:*/
            while (j >= 0 && array[i] >= array[j]) {
                j--;
            }
            int temp = array[i];
            array[i] = array[j];
            array[i] = temp;
        }
        /**step3.index(4)后面的数字反转*/
        Algorithm_Util.reverseArray(array, i + 1);
        return array;
    }

    /**
     * 重新排列, 获取下一个比输入大的组合
     */
    private static int[] nextPermutation_(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int i = array.length - 2;
        while (i >= 0 && array[i] >= array[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = array.length - 1;
            while (array[i] >= array[j]) {
                j--;
            }
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        int startIndex = i + 1;
        int endIndex = array.length - 1;
        while (startIndex < endIndex) {
            int temp = array[startIndex];
            array[startIndex] = array[endIndex];
            array[endIndex] = temp;
            startIndex++;
            endIndex--;
        }

        return array;
    }
}
