package com.interview.javabinterview.sychronized;

/**
 * 实例类
 *
 * @author Ju Baoquan
 * Created at  2019/9/9
 */
public class Something {

    public synchronized void syn_A() { function(); }

    public synchronized void syn_B() { function(); }

    public static synchronized void static_syn_A() { function(); }

    public static synchronized void static_syn_B() { function(); }

    public static void function() {
        int count = 5;
        for (int i = 0; i < 5; i++) {
            count--;
            System.out.println(Thread.currentThread().getName() + " - " + count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

    /** 对象锁 :作用于实例方法 */
    public synchronized void syn() {
        function();
    }

    /** 对象(实例)锁:作用于代码块: this是当前对象(实例) */
    public void code_block_Instance() {
        synchronized (this) {
            function();
        }
    }

    /** 类锁:作用于静态方法 */
    public static synchronized void static_syn() {
        function();
    }

    /** 类锁:作用于代码块: .class: 类锁 */
    public void code_block_Class() {
        synchronized (Something.class) {
            function();
        }
    }
}
