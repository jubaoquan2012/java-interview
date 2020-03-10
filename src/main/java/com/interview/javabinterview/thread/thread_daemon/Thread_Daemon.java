package com.interview.javabinterview.thread.thread_daemon;

/**
 * 演示设置守护线程
 *
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class Thread_Daemon {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("run方法执行");
                }
            }
        });

        /**
         * 设置子线程为守护线程,最后会输出 "用户线程执行结束";
         * 因为main线程为最后一个用户线程, 当最后一个非用户线程执行完.JVM就会自动退出
         * 注意: 即使守护线程是死循环,JVM不等待守护线程运行完毕就会结束JVM进程.
         */
        thread.setDaemon(true);//演示:注释掉这行代码,"run方法执行"会一直执行下去
        //启动子线程
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "用户线程执行结束");
    }
}
