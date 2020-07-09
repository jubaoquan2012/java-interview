package com.interview.javabinterview.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 排序
 * https://www.cnblogs.com/guoyaohua/p/8600214.html
 *
 * @author Ju Baoquan
 * Created at  2020/5/11
 */
public class Algorithm_1 {

    private static void printArray(String methodName, Object result) {
        System.out.println("结果: " + JSON.toJSONString(result) + "---,方法描述: " + methodName);
    }

    public static void main(String[] args) {
        method_1(Algorithm_1::printArray);//冒泡排序
        method_2(Algorithm_1::printArray);//选择排序
        method_3(Algorithm_1::printArray);//插入排序
        method_4(Algorithm_1::printArray);//希尔排序
        method_5(Algorithm_1::printArray);//归并排序
        method_6(Algorithm_1::printArray);//快速排序
        method_7(Algorithm_1::printArray);//计数排序
        method_8(Algorithm_1::printArray);//堆排序
        method_9(Algorithm_1::printArray);//桶排序
        method_10(Algorithm_1::printArray);//基数排序
        method_11(Algorithm_1::printArray);//奇偶排序
        method_12(Algorithm_1::printArray);//给定一个数组，数组中有正数和负数
        method_13(Algorithm_1::printArray);//给定一个数组，按 [x1,y1,x2,y2,...,xn,yn] 格式重新排列
        method_14(Algorithm_1::printArray);//拥有最多糖果的孩子
    }

    private static void method_14(BiConsumer<String, int[]> callback) {
        int[] candies = {2, 3, 5, 1, 3};
        int extraCandies = 3;
        List<Boolean> booleans = kidsWithCandies(candies, extraCandies);
        System.out.println();
    }

    /**
     * 给你一个数组 nums ，数组中有 2n 个元素，按 [x1,x2,...,xn,y1,y2,...,yn] 的格式排列。
     * <p>
     * 请你将数组按 [x1,y1,x2,y2,...,xn,yn] 格式重新排列，返回重排后的数组。
     *
     * @param callback
     */
    private static void method_13(BiConsumer<String, int[]> callback) {
        int[] array = {1, 2, 3, 4, 5, 6};
        int[] resultArray_1 = shuffle_1(array, array.length / 2);
        int[] resultArray_2 = shuffle_2(array, array.length / 2);
        callback.accept("给定数组 2n [x1,x2,...,xn,y1,y2,...,yn],按 [x1,y1,x2,y2,...,xn,yn] 输出", resultArray_1);
    }

    private static void method_12(BiConsumer<String, int[]> callback) {
        int[] array = {-1, -4, -2, 3, -3, -7, 4, 5};
        pnSort(array);
        callback.accept("给定一个数组，数组中有正数和负数,正负数交替排列,保证各自的相对顺序不变", array);
    }

    private static void method_11(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        oddSort(array);
        callback.accept("奇偶排序", array);
    }

    private static void method_10(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        callback.accept("", array);
    }

    private static void method_9(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        callback.accept("", array);
    }

    private static void method_8(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        callback.accept("", array);
    }

    private static void method_7(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        callback.accept("", array);
    }

    private static void method_6(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        quickSort(array, 0, array.length - 1);
        callback.accept("快速排序", array);
    }

    private static void method_5(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        mergeSort(array);
        callback.accept("归并排序", array);
    }

    private static void method_4(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        shellSort(array);
        callback.accept("希尔排序", array);
    }

    private static void method_3(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        insertSort(array);
        callback.accept("插入排序", array);
    }

    private static void method_2(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        selectSort(array);
        callback.accept("选择排序", array);
    }

    private static void method_1(BiConsumer<String, int[]> callback) {
        int[] array = {26, 45, 59, 14, 18, 21, 17, 18};
        bubbleSort(array);
        callback.accept("冒泡排序", array);
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
     * 基本思想：通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，
     * 则可分别对这两部分记录继续进行排序，以达到整个序列有序。
     * <p>
     * 最佳情况：T(n) = O(n)  最差情况：T(n) = O(nlogn)  平均情况：T(n) = O(nlogn)
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
    private static int[] shellSort(int[] array) {
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
    private static int[] mergeSort(int[] arr) {
        if (arr.length < 2) {
            return arr;
        }
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        return mergeSort(mergeSort(left), mergeSort(right));
    }

    private static int[] mergeSort(int[] left, int[] right) {
        int[] integers = new int[left.length + right.length];
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

    /**
     * 给你一个数组 nums ，数组中有 2n 个元素，按 [x1,x2,...,xn,y1,y2,...,yn] 的格式排列。
     * <p>
     * 请你将数组按 [x1,y1,x2,y2,...,xn,yn] 格式重新排列，返回重排后的数组。
     *
     * @param nums
     * @param n
     * @return
     */
    private static int[] shuffle_1(int[] nums, int n) {
        int index = 0;
        int[] result = new int[nums.length];
        for (int i = 0; i < n; i++) {
            result[index++] = nums[i];
            result[index++] = nums[n + i];
        }
        return result;
    }

    private static int[] shuffle_2(int[] nums, int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(nums[i]);
            list.add(nums[i + n]);
        }
        for (int i = 0; i < n * 2; i++) {
            nums[i] = list.get(i);
        }
        return nums;
    }

    /**
     * 对每一个孩子，检查是否存在一种方案，将额外的 extraCandies 个糖果分配给孩子们之后，此孩子有 最多 的糖果。注意，允许有多个孩子同时拥有 最多 的糖果数目。
     * <p>
     * 输入：candies = [2,3,5,1,3], extraCandies = 3
     * 输出：[true,true,true,false,true]
     */
    private static List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int maxCandies = 0;
        for (int candy : candies) {
            if (candy > maxCandies) {
                maxCandies = candy;
            }
        }

        List<Boolean> result = new ArrayList<>(candies.length);
        for (int candy : candies) {
            if ((candy + extraCandies) >= maxCandies) {
                result.add(true);
            } else {
                result.add(false);
            }
        }
        return result;
    }
}
