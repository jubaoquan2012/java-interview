package com.interview.javabinterview.a_algorithm;

/**
 * @author: heshineng
 * @createdBy: 2019/11/26 1:48
 */
public class Algorithm_15 {

    /**
     * 题目：
     * HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。今天测试组开完会后,他又发话了:在古老的一维模式识别中,
     * 常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。
     * 但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？
     * 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。
     * 给一个数组，返回它的最大连续子序列的和，你会不会被他忽悠住？(子向量的长度至少是1)
     */

    public static void main(String[] args) {
        Algorithm_15 test = new Algorithm_15();
        int[] array = {6, -3, -2, 7, -15, 1, 2, 2};
        //int[] array = {-1, -3, -2, -10, -15, -1, -2, -2};
        System.out.println(test.findGreatestSumOfSubArray3(array));
    }

    /**
     * 思路方式：
     * 一个变量记录曾经最大 一个连续子数列的和
     * 一个变量记录当前连续子序列的和
     * 如果当前的连续子序列的和小于0，说明上一个数据是负数，影响之前的结果，
     * 所以跳过上一个负数，（因为需要连续的子序列），跳过序列，和从一个数开始
     * <p>
     * 如果当前得到和，比上一个 连续最佳的和 大，就让将当前和 赋值给上一个
     *
     * @param array
     * @return
     */
    public int findGreatestSumOfSubArray1(int[] array) {
        if (array.length == 0 || array == null) {
            return 0;
        }
        int currentSum = 0;
        Integer greatPreSum = null;
        for (int i = 0; i < array.length; i++) {
            if (currentSum <= 0) {
                //记录当前最大值
                currentSum = array[i];
            } else {
                /**
                 * 只要到上一个元素和 不为负数，就继续连续加下去，可能后面有连续的值需要加
                 * 目的是为了找到最大连续和
                 * 如果加到之前值 结果已经为负数或者为0 说明 经过前面几个数都是负，又因为要连续，
                 * 所以跳过这个 前面的负数，从下一个开始连续 一直到最后，此时 greatPreSum就是 最佳连续最大值
                 */

                //当array[i]为正数时，加上之前的最大值并更新最大值。
                currentSum += array[i];
            }
            /**
             * 如果当前结果值，比上一个连续值，最好的和大，就将曾经的最大值替换成当前 和 的最大值
             */
            if (greatPreSum == null || currentSum > greatPreSum) {
                greatPreSum = currentSum;
            }
        }
        return greatPreSum;
    }

    /**
     * 使用动态规划
     * 使用数组 使用数组的每一位存 前一个连续的结果
     * 并且比较最大的结果值
     *
     * @param array
     * @return
     */
    public int findGreatestSumOfSubArray2(int[] array) {
        if (array.length == 0 || array == null) {
            return 0;
        }
        int len = array.length;
        int[] dp = new int[len];
        int maxResult = array[0];
        dp[0] = array[0];
        for (int i = 1; i < len; i++) {
            //使用上一个 连续结果 加当前位，得到新的结果
            int newMax = dp[i - 1] + array[i];
            if (newMax > array[i]) {
                //如果新的结果 比当前元素 大。则当前结果即为 新加的结果和
                dp[i] = newMax;
            } else {
                //如果新的结果 比当前位小，说明 这个结果变小，比之前连续的小，所以要跳过这一位，但跳过这一位
                //就不连续，所以这个结果和 就是使用当前位去存，使用下一位加
                dp[i] = array[i];
            }

            if (dp[i] > maxResult) {
                //如果新的结果和 比之前 最佳结果和 大 就变成新的结果和
                maxResult = dp[i];
            }
        }
        return maxResult;
    }

    /**
     * 对 findGreatestSumOfSubArray2优化，不需要存所有结果和
     * 只需要存 上一次结果和 和 曾经最佳 结果和
     * <p>
     * 每次利用前面的值，起到动态规划作用
     *
     * @param array
     * @return
     */
    public int findGreatestSumOfSubArray3(int[] array) {
        if (array.length == 0 || array == null) {
            return 0;
        }
        int maxResult = array[0];
        int preResult = array[0];
        for (int i = 1; i < array.length; i++) {
            //使用上一个 连续结果 加当前位，得到新的结果
            int newMax = preResult + array[i];
            if (newMax > array[i]) {
                //如果新的结果 比当前元素 大。则当前结果即为 新加的结果和
                preResult = newMax;
            } else {
                //如果新的结果 比当前位小，说明 这个结果变小，比之前连续的小，所以要跳过这一位，但跳过这一位
                //就不连续，所以这个结果和 就是使用当前位去存，使用下一位加
                preResult = array[i];
            }

            if (preResult > maxResult) {
                //如果新的结果和 比之前 最佳结果和 大 就变成新的结果和
                maxResult = preResult;
            }
        }
        return maxResult;
    }
}
