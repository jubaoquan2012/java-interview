package com.interview.javabinterview.threadstudy.threadCreate;

import java.util.concurrent.Callable;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/10/17
 */
public class ThreadByCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("创建线程成功--Callable");
        return "有返回值";
    }
}
