package com.interview.javabinterview.util;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/3
 */
public class UtilDemo {

    public static void main(String[] args) {
        UtilDemo demo = new UtilDemo();
        demo.retry();
    }

    private void retry() {
        RetryUtils.retry(() -> print());
    }

    private void print() {
        System.out.println("hello");
    }
}
