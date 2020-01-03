package com.interview.javabinterview.java_base.classinit.lesson1;

/**
 * @author jianyun.zhao
 * Created at 2019/11/13
 */
public class Example {
    private String s1 = "s1";

    private static String s2 = "s2";

    private static final String s3 = "s3";
    private static final String s4 = new String("s4");

    static {
        System.out.println("static 代码块");
        s2 = "s2+2";
    }

    {
        System.out.println("一：" + s1);
        s1 = "s1 + 代码块";
        System.out.println("二：" + s1);
    }


    public Example() {

        System.out.println("构造方法：" + s1);
        System.out.println("构造方法");
    }

    {
        System.out.println("代码块");
    }

    public Example(String s1) {
        this.s1 = s1;
    }

    public static void main(String[] args) {
        System.out.println("main方法");
//        System.out.println(s1);

//        System.out.println(s2);
//        System.out.println(s3);
//        System.out.println(s4);

        new Example();
    }

}
