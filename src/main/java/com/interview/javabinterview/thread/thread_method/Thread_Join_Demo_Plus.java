package com.interview.javabinterview.thread.thread_method;

/**
 * join方法作用:
 *
 * 1.创建想成t1和t2.
 * 2.启动线程t1和t2
 * 3.在线程t2中调用线程t1的join方法
 * 4.线程t2获取到t1对象上的锁，然后执行wait()无限期等待:
 * 5.t1执行业务代码
 * 6.t1执行完毕，在关闭t1之前，jvm唤醒所有阻塞在t1对象上的线程 : 最重要的一步
 * 7.t2被唤醒，继续执行
 * 8.t2结束
 *
 * @author Ju Baoquan
 * Created at  2020/3/9
 */
public class Thread_Join_Demo_Plus {

    public static void main(String[] args) {
        int count = 100;
        Task t1 = new Task(count, null);
        t1.setName("t1");
        //在线程t2中调用了t1的join方法,t2需要等待t1执行完循环之后再执行自己的循环
        Task t2 = new Task(count, t1);
        t2.setName("t2");
        t2.start();
        t1.start();
    }

    public static class Task extends Thread {

        private final int count;

        private Task task;

        public Task(int count, Task task) {
            this.task = task;
            this.count = count;
        }

        @Override
        public void run() {
            if (task != null) {
                try {
                    /**
                     * 此处执行的是t1.join-->join(0)--wait(0). 为啥 t2执行wait(0)?  锁对象是 t1?
                     *
                     * 此处代码在线程 t2中: 执行wait(0) Object对象是当前对象t2
                     */
                    task.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < count; i++) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
            }
        }
    }
}
