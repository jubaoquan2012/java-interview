package com.interview.javabinterview.thread.thread_method;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class Thread_Yield_Demo implements Runnable {
    public Thread_Yield_Demo() {
        // 创建并启动线程
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        //当i=0时让出CPU执行权,放弃时间片,进行下一轮调度
        for (int i = 0; i < 5; i++) {
            if ((i % 5) == 0) {
                System.out.println(Thread.currentThread().getName() + "_yield cpu...");
                //当前线程让出CPU执行权,放弃时间片,进行下一轮调度
                //Thread.yield();
            }
        }
        System.out.println(Thread.currentThread().getName() + "_is over");
    }

    public static void main(String[] args) {
        new Thread_Yield_Demo();
        new Thread_Yield_Demo();
        new Thread_Yield_Demo();
    }
}
