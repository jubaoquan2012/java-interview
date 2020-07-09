package com.interview.javabinterview.spring.aop.aspect;

import com.interview.javabinterview.spring.aop.intercept.ZPMethodInterceptor;
import com.interview.javabinterview.spring.aop.intercept.ZPMethodInvocation;

import java.lang.reflect.Method;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
public class ZPAfterReturningAdviceInterceptor extends ZPAbstractAspectAdvice implements ZPMethodInterceptor {

    public ZPAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ZPMethodInvocation invocation) throws Throwable {
        return null;
    }
}
