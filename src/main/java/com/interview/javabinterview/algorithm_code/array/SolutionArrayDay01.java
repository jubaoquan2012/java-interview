package com.interview.javabinterview.algorithm_code.array;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 两数之和
 * 连接:https://leetcode-cn.com/problems/two-sum/
 *
 * @author baoquan.Ju
 * Created at  2020/11/6
 */
public class SolutionArrayDay01 {
    /**
     * 暴力破解
     */
    public int[] twoSum_1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (target - nums[i] == nums[j]) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    /**
     * 时间复杂度 O(N)
     * 借助 hash: key是nums的元素,value当前元素下标
     */
    public int[] twoSum_2(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (numMap.containsKey(target - nums[i])) {
                return new int[]{numMap.get(target - nums[i]), i};
            }
            numMap.put(nums[i], i);
        }
        return new int[0];
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        SolutionArrayDay01 solution = new SolutionArrayDay01();
        System.out.println(JSONObject.toJSONString(solution.twoSum_1(nums, target)));
    }
}
