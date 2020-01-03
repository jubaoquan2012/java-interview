package com.interview.javabinterview.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/20
 */
public class MathTest implements Runnable {

    private ExecutorService executorService;

    public MathTest(int threadCount) {
        this.executorService = Executors.newFixedThreadPool(threadCount);

    }

    public ExecutorService getThreadPool() {
        return this.executorService;
    }

    @Override
    public void run() {

    }
}
