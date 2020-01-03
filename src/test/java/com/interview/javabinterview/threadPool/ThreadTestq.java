package com.interview.javabinterview.threadPool;


import org.junit.jupiter.api.Test;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/20
 */
public class ThreadTestq {

    @Test
    public void test() {
        MathTest mathTest = new MathTest(10);
        mathTest.getThreadPool().execute(mathTest);

        int sum = sum(10);
        System.out.println(sum);
    }

    public int sum(int n) {
        int a = 0;
        for (int i = 1; i < n + 1; i++) {
            a += i;
        }
        return a;
    }

}
