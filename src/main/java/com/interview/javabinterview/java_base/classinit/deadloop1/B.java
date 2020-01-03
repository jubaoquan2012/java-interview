package com.interview.javabinterview.java_base.classinit.deadloop1;

public class B {

    public static A a;

    static {
        System.out.println("B static start");
        a = new A();
        System.out.println("B static end");
    }

    public B() {
        System.out.println("B 构造");
    }
}