package com.interview.javabinterview.spring.springboot.threadpool.impl;

public interface RejectedExecutionHandler {

    void rejectedExecution(Runnable r, ThreadPoolExecutor executor);
}
