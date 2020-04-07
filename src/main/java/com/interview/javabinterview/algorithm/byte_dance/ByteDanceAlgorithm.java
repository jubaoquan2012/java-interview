package com.interview.javabinterview.algorithm.byte_dance;

import com.interview.javabinterview.algorithm.AlgorithmUtils;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class ByteDanceAlgorithm {
    //https://blog.csdn.net/seagal890/article/details/93924905  求一个数组中正数和负数交替出现的最长子数组（JAVA代码）

    private static int[] array_1 = {26, 45, 59, 14, 18, 21, 17, 18};

    private static int[] array_2 = {1, -1, -2, -3, -4, -5, 2, 3, 4, 5};

    public static void main(String[] args) {
        //System.out.println(reverseString("I am a student."));
        //        quickSort(array_1, 0, array_1.length - 1);
        //        sort(array_1);
        //        oddSort(array_1);
        //        test(array_1, 2, 5);
        pnSort(array_2);
        AlgorithmUtils.print(array_2);
        //AlgorithmUtils.reverse(array_2);
    }

    /**
     * 快速排序
     */
    private static void quickSort(int[] array, int low, int high) {
        if (array.length == 0 || low >= high) {
            return;
        }
        //1.暂定一个分治点
        int pivot = array[low];
        int left = low, right = high;

        //2.根据分治点,分为两个数组:小于pivot一组、大于pivot一组
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

        //3.把pivot放在两组组中间位置: low和pivot交换位置
        array[low] = array[left];
        array[left] = pivot;

        //4.迭代两组
        quickSort(array, low, left - 1);
        quickSort(array, left + 1, high);
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

    private static String reverseString(String str) {
        String[] seq = str.split(" ");
        int start = 0;
        int end = seq.length - 1;
        while (start < end) {
            String temp = seq[start];
            seq[start] = seq[end];
            seq[end] = temp;
            start++;
            end--;
        }

        StringBuilder builder = new StringBuilder();
        for (String s : seq) {
            builder.append(s).append(" ");
        }
        return builder.toString().trim();
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

    /**
     * 把endIndex 的数插入到beginIndex后面
     * 1 2 3 4 5 6 7 8 9 把 8 插入到 3 的后面 其他的相对位置不变
     * 1 2 3 8 4 5 6 7 9
     *
     * @return
     */
    private static int[] test(int[] array, int beginIndex, int endIndex) {
        //第一步, 把endIndex临时保存
        int temp = array[endIndex];
        //第二步, 从
        for (int i = endIndex; i > beginIndex; i--) {
            array[i] = array[i - 1];
        }
        //第三步,将最后endIndex 放入到beginIndex+1
        array[beginIndex + 1] = temp;
        return array;
    }
}
