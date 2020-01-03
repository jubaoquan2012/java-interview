package com.interview.javabinterview.threadstudy.threadwait;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/10/17
 */
public class ThreadWaitDemo2 {

    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    /**
     *  threadA get resourceA thread
     *  threadA get resourceB thread
     *  threadA release resourceA thread
     *  threadB get resourceA thread
     *  threadB try get resourceB thread...
     */
    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 获取resourceA共享资源的监视器资源
                    synchronized (resourceA) {
                        System.out.println("threadA get resourceA thread");

                        // 获取resourceB共享资源的监视器资源
                        synchronized (resourceB) {
                            System.out.println("threadA get resourceB thread");

                            //线程A阻塞,并释放获取到的resourceA的锁
                            System.out.println("threadA release resourceA thread");
                            //Thread.sleep(10000);
                            resourceA.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    // 获取resourceA共享资源的监视器资源
                    synchronized (resourceA) {
                        System.out.println("threadB get resourceA thread");

                        System.out.println("threadB try get resourceB thread...");
                        // 获取resourceB共享资源的监视器资源
                        synchronized (resourceB) {
                            System.out.println("threadB get resourceB thread");

                            //线程A阻塞,并释放获取到的resourceA的锁
                            System.out.println("threadB release resourceA thread");
                            resourceA.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadA.start();
        threadB.start();

        //等待两个线程结束
        threadA.join();
        threadB.join();
        System.out.println("main over");
    }
}
