package com.interview.javabinterview.thread.thread_method;

/**
 * 线程wait()/wait(long timeout) 测试
 *
 * @author Ju Baoquan
 * Created at  2020/3/6
 */
public class ThreadWait_Demo extends Thread {
    public ThreadWait_Demo(String name) {
        super(name);
    }
    @Override
    public void run() {
        synchronized (this) {
            try {
                Thread.sleep(2000);//使当前线程阻塞1s, 确保主程序t1.wait();执行之后再执行notify()
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " call notify()");
            this.notify();
        }
    }

    public static void main(String[] args) {
        ThreadWait_Demo t1 = new ThreadWait_Demo("t1");
        synchronized (t1){
            try {
                //启动线"t1"
                System.out.println(Thread.currentThread().getName() + " start t1");
                t1.start();
                // 主线程等待t1通过notify唤醒
                System.out.println(Thread.currentThread().getName() + " wait()");
                t1.wait();
                System.out.println(Thread.currentThread().getName() + " continue");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
