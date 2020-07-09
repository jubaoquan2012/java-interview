package com.interview.javabinterview.lamdba.exam;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/28
 */
public class PrintImpl {

    public static void printWithNoParameter() {
        System.out.println("hello");
    }

    public static void printWithOneParameter(String str) {
        System.out.println("hello:" + str);
    }

    public static void printWithTwoParameter(String str1, String str2) {
        System.out.println("hello:" + str1 + "--" + str2);
    }
}
