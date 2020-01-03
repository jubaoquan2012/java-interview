package com.interview.javabinterview.java_base.classinit.deadloop2;

public class A {

    public  static final B b;

    static {
        System.out.println("A static start");
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        b = new B();
        System.out.println("A static end");

    }

    public A() {
        System.out.println("a 构造");
    }

}