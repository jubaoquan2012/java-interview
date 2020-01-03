package com.interview.javabinterview.java_base.classinit.deadloop2;

/**
 * @author jianyun.zhao
 * Created at 2019/11/13
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Main main");
        Thread thread1 = new Thread(() -> new A());
        Thread thread2 = new Thread(() -> new B());
        thread1.start();
        thread2.start();
    }
}
