package com.interview.javabinterview.java_basis;

/**
 * String 相关的基础操作
 *
 * @author Ju Baoquan
 * Created at  2020/3/5
 */
public class StringBasis {

    public static void main(String[] args) {
        stringAppend();
    }

    public static void stringAppend() {
        String str1 = "111";
        String str2 = "111";
        String str3 = str1 + "";
        System.out.println(str2 == str3);
    }
}
