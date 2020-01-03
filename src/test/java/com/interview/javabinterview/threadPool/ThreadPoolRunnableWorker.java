package com.interview.javabinterview.threadPool;

/**
 * @author shineng.he
 * createTime 2019/4/23 16:11
 */
public class ThreadPoolRunnableWorker implements Runnable {

    private int taskCode;

    public ThreadPoolRunnableWorker(int taskCode) {
        this.taskCode = taskCode;
    }

    public int getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(int taskCode) {
        this.taskCode = taskCode;
    }

    @Override
    public void run() {
        sleep(1000);
        System.out.println();
        System.out.println("task 任务完成 taskCode="+taskCode);
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("任务中断，taskCode="+taskCode);
        }
    }
}
