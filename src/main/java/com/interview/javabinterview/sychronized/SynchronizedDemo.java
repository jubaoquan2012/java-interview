package com.interview.javabinterview.sychronized;

import java.util.concurrent.TimeUnit;

/**
 * 类锁和方法锁区别案例
 *
 * @author Ju Baoquan
 * Created at  2019/9/9
 */
public class SynchronizedDemo {

    /**
     * 锁分类:
     * static synchronized          锁的是类(类锁)，
     * synchronized                 锁的是实例对象(对象锁)
     * synchronized(this){} 代码块   锁的是实例对象(对象锁)
     * synchronized(class){} 代码块  锁的是类((类锁))
     * <p>
     * 类锁:   对静态同步方法和 synchronized(class){} 代码块 约束
     * 对象锁: 对实例同步方法和 synchronized(this) {} 代码块 约束
     * <p>
     * 锁的范围(考虑交叉: 类锁和对象锁)
     * 思路: 什么锁: 是否同一类或同一实例: 几把锁 -->确定是否阻塞
     * 对象锁与对象锁(同一个对象实例), 一把锁.阻塞
     * 对象锁与对象锁(不同 对象实例),  两把锁.不阻塞
     * 类锁  与对类锁(同一个class对象),一把锁.阻塞
     * 类锁  与 类锁(不 同class对象), 两把锁.不阻塞
     * 对象锁与 类锁:不同类别的锁,两把锁:不阻塞
     * 例如:
     * x.syn_A()与x.syn_B() :对象锁与对象锁:同一实例:同一把锁-->阻塞,不能同时执行
     * x.syn_A()与y.syn_A() :对象锁与对象锁:不同实例:两 把锁-->不阻塞,能同时执行
     * x.static_syn_A()与y.static_syn_B():类锁与类锁:同一class,同一把锁-->阻塞,不能同时执行
     * x.syn_A()与 x.static_syn_A(): 对象锁与类锁: 两把锁-->不阻塞,能同时执行
     * x.static_syn_A()与 x.code_block_Class():类锁与类锁:同一class,同一把锁-->阻塞,不能同时执行
     * ...
     */

    public static void main(String[] args) throws InterruptedException {
        test_A();
        test_B();
        test_B_B();
        test_C();
        test_D();
        test_E();
        test_F();
        test_G();
        test_H();
    }

    /**
     * 对象锁和对象锁(同一个实例对象),同一把锁: 不能执行
     */
    private static void test_A() throws InterruptedException {
        System.out.println("test_A");
        Something x = new Something();
        new Thread(() -> x.syn_A(), "threadA").start();
        new Thread(() -> x.syn_B(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 对象锁和对象锁(不同实例对象), 两把锁: 可以同时执行.
     */
    private static void test_B() throws InterruptedException {
        System.out.println("test_B");
        Something x = new Something();
        Something y = new Something();
        new Thread(() -> x.syn_A(), "threadA").start();
        new Thread(() -> y.syn_B(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 类锁和类锁(同一个类),同一把锁: 不能同时执行
     */
    private static void test_B_B() throws InterruptedException {
        System.out.println("test_B_B");
        Something x = new Something();
        Something y = new Something();
        new Thread(() -> x.static_syn_A(), "threadA").start();
        new Thread(() -> y.static_syn_B(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 类锁和类锁 (同一个类),同一把锁:不能同时执行.
     */
    private static void test_C() throws InterruptedException {
        System.out.println("test_C");
        new Thread(() -> Something.static_syn_A(), "threadA").start();
        new Thread(() -> Something.static_syn_B(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 对象锁和类锁, 两把锁 : 可以同时执行.
     */
    private static void test_D() throws InterruptedException {
        System.out.println("test_D");
        Something x = new Something();
        new Thread(() -> x.syn_A(), "threadA").start();
        new Thread(() -> x.static_syn_A(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 对象锁和类锁, 两把锁:可以同时执行
     */
    private static void test_E() throws InterruptedException {
        System.out.println("test_E");
        Something x = new Something();
        new Thread(() -> x.syn_A(), "threadA").start();
        new Thread(() -> Something.static_syn_B(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 类锁和类锁(同一个类), 同一把锁: 不可以同时执行
     */
    private static void test_F() throws InterruptedException {
        System.out.println("test_F");
        Something x = new Something();
        new Thread(() -> x.static_syn_A(), "threadA").start();
        new Thread(() -> Something.static_syn_B(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 类锁和类锁(同一个类),同一把锁: 不能同时执行
     */
    private static void test_G() throws InterruptedException {
        System.out.println("test_G");
        Something x = new Something();
        new Thread(() -> x.static_syn_A(), "threadA").start();
        new Thread(() -> x.code_block_Class(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 类锁和对象锁, 两把锁: 可以同时执行,
     *
     * @throws InterruptedException
     */
    private static void test_H() throws InterruptedException {
        System.out.println("test_H");
        Something x = new Something();
        new Thread(() -> x.static_syn_A(), "threadA").start();
        new Thread(() -> x.code_block_Instance(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }
}
