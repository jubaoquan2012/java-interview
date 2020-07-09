package com.interview.javabinterview.spring.aop;

/**
 * 接口
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
public interface ZPAopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
