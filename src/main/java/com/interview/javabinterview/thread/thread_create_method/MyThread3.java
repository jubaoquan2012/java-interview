package com.interview.javabinterview.thread.thread_create_method;

import java.util.concurrent.Callable;

/**
 * 实现Callable
 *
 * @author Ju Baoquan
 * Created at  2020/3/6
 */
public class MyThread3 implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "返回String类型值";
    }
}
