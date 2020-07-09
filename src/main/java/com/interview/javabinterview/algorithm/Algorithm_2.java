package com.interview.javabinterview.algorithm;

/**
 * 二分查找
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_2 {

    public static void main(String[] args) {
        int[] array = Algorithm_Base.array;
        int[] arraySort = Algorithm_Base.arraySort;
        System.out.println(binarySearch(arraySort, 0, arraySort.length, 4));
        System.out.println(binarySearch(arraySort, 4));
    }

    /**
     * 二分查找(递归)
     * <p>
     * mid = (low + high) / 2       计算出的(low + high)存在着溢出的风险
     * mid = low + (high – low)/2  计算出来的mid，一定大于low，小于high，不存在溢出的问题
     *
     * @param array 数组
     * @param low 低位
     * @param high 高位
     * @param target 目标值
     */
    private static int binarySearch(int[] array, int low, int high, int target) {
        if (array.length == 0 || low > high) {
            return -1;
        }
        int middleIndex = low + (high - low) / 2;
        if (array[middleIndex] > target) {
            return binarySearch(array, low, middleIndex - 1, target);
        }
        if (array[middleIndex] < target) {
            return binarySearch(array, middleIndex + 1, high, target);
        }
        return middleIndex;
    }

    /**
     * 二分查找(非递归)
     *
     * @param array 数组
     * @param target 目标值
     * @return
     */
    private static int binarySearch(int[] array, int target) {
        if (array.length == 0) {
            return -1;
        }
        int low = 0;
        int high = array.length;
        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (array[middle] > target) {
                high = middle - 1;
            } else if (array[middle] < target) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }
}
