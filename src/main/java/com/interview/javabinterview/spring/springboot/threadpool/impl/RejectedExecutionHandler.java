package com.interview.javabinterview.spring.threadpool.impl;

public interface RejectedExecutionHandler {

    void rejectedExecution(Runnable r, ThreadPoolExecutor executor);
}
