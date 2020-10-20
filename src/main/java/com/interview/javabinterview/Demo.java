package com.interview.javabinterview;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/26
 */
public class Demo {

    private String str = "jubaoquan"; //自变量 进入常量池

    //JVM
    public static void main(String[] args) throws Exception{
        JSONS
        Demo demo = new Demo();
        //        String s = "abc";
        //        String x = "aaa";
        //        String p = s + x;//进入
        //        String p12 = s + "ddd";//进入
        //        String p13 = "ssss" + "ddd";//进入
        //        String s1 = new String("jubaoquan");
        //        for (int i = 0; i < 10; i++) {
        //            String a = "123" + i;//只要是有变量就不是自变量.
        //        }
        //        demo.strDetail(demo.str, p);
        demo.strDetail();



    }

    public void strDetail() {
        String str1 = new String("abc");
        String b1 = str1.intern();
        System.out.println(str1 == b1);

        String str2 = "abc";
        String b2 = str2.intern();
        System.out.println(str2 == b2);

        System.out.println("---------");

        String s1 = "abc";//在常量池中: abc
        String s2 = new String(s1);
        String s3 = s2.intern();

        int b = 2;

        int a = 3;
        int c = a + b;

        /**
         *  == : 比较的 物理地址
         *  字面量
         */
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s2 == s3);

        System.out.println(s1.equals(s2));
        System.out.println(s2);
    }
}
