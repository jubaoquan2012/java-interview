package com.interview.javabinterview.a_algorithm;

/**
 * 斐波那契数列
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_3 {


    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            //System.out.println(FibonacciSeq_1(i));
            //System.out.println(FibonacciSeq_2(i));
            System.out.println(FibonacciSeq_3(i));
        }
    }

    /**
     * 递归实现
     *
     * @param n
     * @return
     */
    private static int FibonacciSeq_1(int n) {
        if (n <= 0) {
            return -1;
        }
        if (n <= 2) {
            return 1;
        } else {
            return FibonacciSeq_1(n - 1) + FibonacciSeq_1(n - 2);
        }
    }

    /**
     * 非递归实现
     *
     * @param n 数
     * @return 结果
     */
    private static int FibonacciSeq_2(int n) {
        if (n <= 0) {
            return -1;
        }
        int first = 1;
        int two = 1;
        int third = 1;
        while (n > 2) {
            third = first + two;
            first = two;
            two = third;
            n--;
        }
        return third;
    }

    /**
     * 尾递归实现
     *
     * @param n
     * @return
     */
    private static int FibonacciSeq_3(int n) {
        if (n <= 2 ) {
            return 1;
        }
        return fibo(n, 1, 1, 3);
    }

    private static int fibo(int n, int r1, int r2, int begin) {
        if (n == begin) {
            return r1 + r2;
        }
        return fibo(n, r2, r1 + r2, ++begin);
    }
}
