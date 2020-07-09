package com.interview.javabinterview.spring.aop.intercept;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
public interface ZPMethodInterceptor {

    Object invoke(ZPMethodInvocation invocation) throws Throwable;
}
