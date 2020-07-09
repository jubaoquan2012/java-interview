package com.interview.javabinterview.algorithm;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/26
 */
public class Algorithm_18 {

    public static void main(String[] args) {
        System.out.println(subtractProductAndSum(4421));
    }

    private static int subtractProductAndSum(int n) {
        int sum = 0;
        int multi = 1;
        while (n > 0) {
            int indexNum = n % 10;
            sum += indexNum;
            multi *= indexNum;
            n = n / 10;
        }
        return multi - sum;
    }
}
