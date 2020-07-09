package com.interview.javabinterview.algorithm;

/**
 * 回文数
 * 判断一个整数是否是回文数.
 * 回文数是指正序(从左到右)和倒叙(从右到左)读都是一样的整数
 *
 * @author Ju Baoquan
 * Created at  2020/6/9
 */
public class Algorithm_19 {

    public static void main(String[] args) {
        int num = 1234321;
        System.out.println(isPalindromeWithNumReverse(num));
        System.out.println(isPalindromeWithStrReverse(num));
        System.out.println(isPalindromeWithIndex(num));
    }

    /**
     * 数字反转,然后新旧两个数比较
     */
    private static boolean isPalindromeWithNumReverse(int num) {
        if (num <= 0) {
            return false;
        }
        int oldNum = num;
        int newNum = 0;
        while (num != 0) {
            //取出最后一位
            int lastIndexNum = num % 10;
            //减少一位
            num = num / 10;
            //把这个数加到newNum的末尾
            newNum = newNum * 10 + lastIndexNum;
        }
        return oldNum == newNum;
    }

    /**
     * 字符串反转比较
     */
    private static boolean isPalindromeWithStrReverse(int num) {
        if (num <= 0) {
            return false;
        }
        String oldStr = Integer.toString(num);
        StringBuilder newStr = new StringBuilder();
        for (int i = oldStr.length() - 1; i >= 0; i--) {
            char c = oldStr.charAt(i);
            newStr.append(c);
        }
        return oldStr.equals(newStr.toString());
    }

    /**
     * 数字位比较
     */
    private static boolean isPalindromeWithIndex(int num) {
        if (num <= 0) {
            return false;
        }
        int initDev = 1;
        while (num / initDev >= 10) {
            initDev = initDev * 10;
        }
        while (num != 0) {
            int left = num / initDev;
            int right = num % 10;
            if (left != right) {
                return false;
            }
            num = num % initDev / 10;// 去头去尾 : 12321 - > 232 : (12321 % 10000) /10 = 232
            initDev = initDev / 100;
        }
        return true;
    }
}
