package com.interview.javabinterview.java_base.string;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/7/9
 */
public class StringDemo {

    public static void main(String[] args) {
        method_1();
        method_2();
        method_3();
        method_4();
    }

    /**
     * ==: 引用类型 比较的的是地址
     * equals: 引用类型比较的也是"=="地址比较,String是重写了,比较的"值"
     *
     * 1.String s = new String(“hello”)会创建2（1）个对象，String s = “hello”创建1（0）个对象。
     *  注：当字符串常量池中有对象hello时括号内成立！
     * 2.字符串如果是变量相加，先开空间，在拼接。
     * 3. 字符串如果是常量相加，是先加，然后在常量池找，如果有就直接返回，否则，就创建。
     *
     */
    private static void method_1() {
        String new_str_1 = new String("abc");
        String new_str_2 = new String("abcd");
        String str_1 = "abc";
        String str_2 = "d";
        String str_3 = "abcd";
        String str_4 = str_1;
        String str_5 = "abc" + "d";
        String str_6 = str_1 + str_2;
        System.out.println(str_1 == str_4);
        System.out.println(str_1.equals(str_4));

        System.out.println(str_3 == str_5);
        System.out.println(str_3 == "abc" + "d");
        System.out.println(str_3.equals(str_5));

        System.out.println(str_3==str_6);
        System.out.println(str_3.equals(str_6));
    }

    private static void method_2() {
    }

    private static void method_3() {
    }

    private static void method_4() {
    }
}
