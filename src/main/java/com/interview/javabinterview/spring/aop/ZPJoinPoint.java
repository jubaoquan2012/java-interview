package com.interview.javabinterview.spring.aop;

import java.lang.reflect.Method;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
public interface ZPJoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();
}
