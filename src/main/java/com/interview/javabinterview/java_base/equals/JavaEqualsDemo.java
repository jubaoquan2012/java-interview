package com.interview.javabinterview.java_base.equals;

import java.util.Scanner;

/**
 * "=="
 * 在基本类型中:
 * 比较的是两个基本数据类型的值是否相等.
 *  int a = 10 ;
 *  int b = 10;
 *   a==b : true
 * 在引用类型中:
 *  比较的是引用的对象是否相等,即是否引用了同一个对象
 *  new 的两个对象在栈中是两个地址;
 *
 *  "equals"
 *  String 重写equals 方法, 循环比较字符是否相等
 *
 *  普通Object equals: 比较的是俩个对象的引用地址是否是一个
 *  return (this == obj);
 *
 * @author Ju Baoquan
 * Created at  2020/4/13
 */
public class JavaEqualsDemo {

    public static void main(String[] args) {
        //基本数据类型的比较
        int num1 = 10;
        int num2 = 10;
        System.out.println(num1 == num2);   //true
        //引用数据类型的比较
        String s1 = "chance";
        String s2 = "chance";
        System.out.println(s1 == s2);        //true
        System.out.println(s1.equals(s2));    //true
        //String类中==与equals的比较
        String s3 = new String("chance");
        String s4 = new String("chance");
        System.out.println(s3 == s4);        //false
        System.out.println(s3.equals(s4));    //true
        //非String类中==与equals类型的比较
        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        System.out.println(scanner.equals(scanner2));       //false
        Scanner sc = scanner;
        System.out.println(scanner.equals(sc));            //true
    }
}
