package com.interview.javabinterview.threadstudy.threadwait;

/**
 * 模拟生产和消费的例子
 *
 * @author Ju Baoquan
 * Created at  2019/10/17
 */
public class ThreadWaitDemo3 {

    static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("---begin----");
                    synchronized (obj) {

                        obj.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadA.start();
        Thread.sleep(1000);
        System.out.println("---begin interrupt threadA---");
        threadA.interrupt();
        System.out.println("---end interrupt threadA");
    }
}
