package com.interview.javabinterview.java_base.classinit.deadloop2;

public class B {

    public static final A a;

    static {
        System.out.println("B static start");
        try {
            Thread.sleep(6*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a = new A();
        System.out.println("B static end");
    }

    public B() {
        System.out.println("B 构造");
    }
}