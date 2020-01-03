package com.interview.javabinterview.java_base.classinit.deadloop1;

/**
 * @author jianyun.zhao
 * Created at 2019/11/13
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Main main");

        System.out.println(new A());
    }
}
