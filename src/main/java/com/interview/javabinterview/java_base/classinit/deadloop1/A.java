package com.interview.javabinterview.java_base.classinit.deadloop1;

public class A {

    public static B b;

    static {
        System.out.println("A static start");
        b = new B();
        System.out.println("A static end");

    }

    public A() {
        System.out.println("a 构造");
    }
}