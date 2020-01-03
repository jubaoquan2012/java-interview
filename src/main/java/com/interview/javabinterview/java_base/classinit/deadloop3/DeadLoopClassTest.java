package com.interview.javabinterview.java_base.classinit.deadloop3;

/**
 * @author jianyun.zhao
 * Created at 2019/11/13
 */
public class DeadLoopClassTest {

    public static void main(String[] args) {
        Runnable script = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread() + " run over");
            }
        };
        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}
