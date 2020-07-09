package com.interview.javabinterview.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/9
 */
public class Algorithm_20 {

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};

        List<List<Integer>> result = threeSum(nums, 0);
        System.out.println(JSON.toJSONString(result));

        int[] num = {2, 7, 11, 15};
        int[] ints = twoSum(num, 18);
        System.out.println(JSON.toJSONString(ints));
    }

    /**
     * 标签：三数之和
     * <p>
     * .首先对数组进行排序，排序后固定一个数 nums[i]，再使用左右指针指向 nums[i]后面的两端，数字分别为 nums[L] 和 nums[R]，计算三个数的
     * 和 sum,判断是否满足为 0，满足则添加进结果集
     * .如果 nums[i]大于 0，则三数之和必然无法等于 0，结束循环
     * .如果 nums[i] == nums[i-1]，则说明该数字重复，会导致结果重复，所以应该跳过
     * .当 sum == 0 时，nums[L] == nums[L+1] 则会导致结果重复，应该跳过，L++
     * .当 sum == 0 时，nums[R] == nums[R-1] 则会导致结果重复，应该跳过，R--
     * .时间复杂度：O(n^2),n 为数组长度
     */

    public static List<List<Integer>> threeSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        int len = nums.length;
        if (len < 3) {
            return ans;
        }
        //排序
        Arrays.sort(nums);
        for (int i = 0; i < len; i++) {
            //判断第一个元素是否大于目标值
            if (nums[i] >= target) {
                break;
            }
            //去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int L = i + 1;
            int R = len - 1;
            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];
                if (sum == target) {
                    ans.add(Arrays.asList(nums[i], nums[L], nums[R]));
                    //去重
                    while (L < R && nums[L] == nums[L + 1]) {
                        L++;
                    }
                    //去重
                    while (L < R && nums[R] == nums[R - 1]) {
                        R--;
                    }
                    L++;
                    R--;
                } else if (sum < target) {
                    L++;
                } else {
                    R--;
                }
            }
        }
        return ans;
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                return new int[]{map.get(nums[i]), i};
            }
            map.put((target - nums[i]), i);
        }

        throw new IllegalArgumentException("no two sum");
    }
}
