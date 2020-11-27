package com.interview.javabinterview.netty;

/**
 * Description:
 *
 * @author baoquan.Ju
 * Created at  2020/11/12
 */
public class Demo {
    public static void main(String[] args) {
        String str1 = "eee";
        String str2 = "eee";
        String str3 = new String("eee");
        System.out.println("str1 == str2 is " + (str1 == str2));
        System.out.println("str1 == str3 is " + (str1 == str3));
        System.out.println("str1.equals(str2) is " + str1.equals(str2));
        System.out.println("str1.equals(str3) is " + str1.equals(str3));
    }
}
