package com.interview.javabinterview.java_base.classinit.lesson1;

class Test {
    public static Test test = new Test();

    public static void main(String[] args) {
        new Test();
        System.out.println(add(1, 2));
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
