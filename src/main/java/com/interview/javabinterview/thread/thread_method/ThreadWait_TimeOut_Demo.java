package com.interview.javabinterview.thread.thread_method;

/**
 * 线程wait()/wait(long timeout) 测试
 *
 * @author Ju Baoquan
 * Created at  2020/3/6
 */
public class ThreadWait_TimeOut_Demo extends Thread {

    public ThreadWait_TimeOut_Demo(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " run");
        // 死循环，不断运行。
        while (true) {
           ;
        }//  这个线程与主线程无关，无 synchronized
    }

    public static void main(String[] args) {
        ThreadWait_TimeOut_Demo t1 = new ThreadWait_TimeOut_Demo("t1");
        synchronized (t1) {
            try {
                //启动线"t1"
                System.out.println(Thread.currentThread().getName() + " start() t1");
                t1.start();
                //主线程等待t1通过notify()唤醒 或 notifyAll()唤醒，或超过3000ms延时；然后才被唤醒。
                System.out.println(Thread.currentThread().getName() + " wait()");
                t1.wait(3000);
                System.out.println(Thread.currentThread().getName() + " continue");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
