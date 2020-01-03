package com.interview.javabinterview.sychronized;


import java.util.concurrent.TimeUnit;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/9/9
 */
public class SynchronizedDemo {

    /**
     * a. x.isSyncA()与x.isSyncB()           //不能交替执行--> 不能同时访问
     * b. x.cSyncA()与y.cSyncB()             //不能交替执行--> 不能同时访问
     * c. x.cSyncA()与与Something.cSyncB()   //不能交替执行--> 不能同时访问
     * d. x.isSyncA()与y.isSyncA()           //可以交替执行--> 可以同时访问
     * e. x.isSyncA()x.cSyncA()              //可以交替执行--> 可以同时访问
     * f. x.isSyncA()与Something.cSyncA()    //可以交替执行--> 可以同时访问
     * <p>
     * 总结:
     * static synchronized 锁的是类对象(锁是类)，synchronized 锁的是实例对象(锁时当前对象)。
     * 若类对象被lock，则类对象的所有同步方法全被lock；
     * 若实例对象被lock，则该实例对象的所有同步方法全被lock。
     */

    public void a() throws InterruptedException {
        Something x = new Something();
        new Thread(() -> x.isSyncA(), "threadA").start();
        new Thread(() -> x.isSyncB(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    public void b() throws InterruptedException {
        Something x = new Something();
        Something y = new Something();

        new Thread(() -> x.isSyncA(), "threadA").start();
        new Thread(() -> x.isSyncA(), "threadB").start();

        TimeUnit.SECONDS.sleep(10);
    }

    public void c() throws InterruptedException {
        Something x = new Something();
        Something y = new Something();
        // x.cSyncA()与x.cSyncB()
        // x.cSyncA()与y.cSyncA()
        // x.cSyncA()与y.cSyncB()

        new Thread(() -> x.cSyncA(), "threadA").start();
        new Thread(() -> y.cSyncB(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    public void d() throws InterruptedException {
        Something x = new Something();
        Something y = new Something();
        new Thread(() -> x.cSyncB(), "threadA").start();
        new Thread(() -> y.cSyncA(), "threadB").start();

//        new Thread(() -> x.isSyncA(), "threadA").start();
//        new Thread(() -> x.isSyncB(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
    }

    public void e() throws InterruptedException {
        Something x = new Something();
//        new Thread(() -> x.isSyncA(), "threadA").start();
//        new Thread(() -> Something.cSyncB(), "threadB").start();
        new Thread(() -> x.cSyncA(), "threadA").start();
        new Thread(() -> Something.cSyncB(), "threadB").start();
        TimeUnit.SECONDS.sleep(10);
        Thread thread = new Thread();
        thread.join();
    }
}
