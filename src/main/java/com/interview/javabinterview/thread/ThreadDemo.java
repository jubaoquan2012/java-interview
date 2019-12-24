package com.interview.javabinterview.thread;

import com.interview.javabinterview.thread.aqs.Condition;
import com.interview.javabinterview.thread.aqs.lockimpl.ReentrantLock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("实现Callable");
                return "null";
            }
        });
        String result = futureTask.get();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("实现runnable");
            }
        });

        Thread thread = new Thread();
        thread.start();
        thread.interrupt();
        thread.isInterrupted();
        Thread.interrupted();

        ReentrantLock lock1 = new ReentrantLock();
        Condition condition = lock1.newCondition();
        condition.await();
        lock1.lock();
    }
}
