package com.interview.javabinterview.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.function.BiConsumer;

/**
 * 数组去重
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_8 {

    private static void printArray(String methodName, Object result) {
        System.out.println("结果: " + JSON.toJSONString(result) + "---,方法描述: " + methodName);
    }

    public static void main(String[] args) {
        method_1(Algorithm_8::printArray);
    }

    private static void method_1(BiConsumer<String, int[]> callback) {
        int[] array = {1, 2, 2, 2, 3, 4, 5, 6, 6};
        int[] ints = removeDuplicates(array);
        callback.accept("冒泡排序", ints);
    }

    /**
     * 快慢指针,当快慢指针元素不同,
     *
     * @param array
     * @return
     */
    private static int[] removeDuplicates(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int slow = 0;
        for (int fast = 0; fast < array.length; fast++) {
            if (array[slow] != array[fast]) {
                array[++slow] = array[fast];
            }
        }
        int length = slow + 1;

        int[] newArray = new int[length];
        for (int i = 0; i < length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }
}
