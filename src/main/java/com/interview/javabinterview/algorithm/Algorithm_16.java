package com.interview.javabinterview.algorithm;

/**
 * 给定一个长度为n的数组.
 * 数组的元素的取值范围为0--(n-1).
 * 里面可能有重复的元素.
 * <p>
 * 1.要求时间复杂度为 O(n),找出任意一个重复的数字;
 * 2.要求时间复杂度为 O(n),找第一个重复的数组的下标;
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_16 {

    public static void main(String[] args) {
        int[] array = {1, 3, 3, 2};

        System.out.println(findDuplicate(array));

        //System.out.println(getFirstRepeat_2(array));
    }

    public static int findDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int[] newNums = new int[nums.length];
        for (int num : nums) {
            if (newNums[num] == 1) {
                return num;
            } else {
                newNums[num] = 1;
            }
        }
        return -1;
    }


    private static int getFirstRepeat_2(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            //如果取出来的值在正确的位置,开始下次循环
            if (i == array[i]) {
                continue;
            }
            int indexNum = array[i];
            //在array[indexNum] 和 indexNum的值相同直接返回
            if (indexNum == array[indexNum]) {
                return indexNum;
            }
            //如果都不满足,把 array[i] 和 array[array[i]]交换位置
            array[i] = array[indexNum];
            array[indexNum] = indexNum;
        }
        return -1;
    }
}

