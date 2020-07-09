package com.interview.javabinterview.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/26
 */
public class Algorithm_17 {

    public static void main(String[] args) {
        //        String str = "pwwkewa";
        //        int length = lengthOfLongestNoRepeat(str);
        //        System.out.println(length);

        //System.out.println(subtractProductAndSum(4421));
    }

    /**
     * 思路: 利用滑动窗口
     * 1.一个集合记录:<key,value>(key:字符,value:字符最后一次出现的下标+1)
     * 2.计算maxLength: 比较 maxLength = Math.max(maxLength,窗口的长度);
     *
     * @param s
     * @return
     */
    private static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }

    /**
     * kewa
     * <p>
     * p 1 : 0 - 0 +1
     * w 2 : 1 - 0 +1
     * w 3 :
     */
    private static int lengthOfLongestNoRepeat(String str) {
        //初始化最大不连续
        int maxLength = 0;
        //定义窗口起始下标
        int startIndex = 0;
        //定义一个集合: 保存字符当前下标<字符,字符的下标>
        Map<Character, Integer> map = new HashMap<>();
        for (int endIndex = 0; endIndex < str.length(); endIndex++) {
            char c = str.charAt(endIndex);
            if (map.containsKey(c)) {
                //获取上次该字符出现的下标,作为窗口的起始位置(因为要求不重复)
                startIndex = Math.max(map.get(c), startIndex);
            }
            //获取窗口的宽度
            int length = endIndex - startIndex + 1;
            if (length >= maxLength) {
                maxLength = length;
            }
            //记录字符本次的下标
            map.put(c, endIndex + 1);
        }
        return maxLength;
    }
}
