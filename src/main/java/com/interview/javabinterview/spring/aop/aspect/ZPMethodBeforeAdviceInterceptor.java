package com.interview.javabinterview.spring.aop.aspect;

import com.interview.javabinterview.spring.aop.ZPJoinPoint;
import com.interview.javabinterview.spring.aop.intercept.ZPMethodInterceptor;
import com.interview.javabinterview.spring.aop.intercept.ZPMethodInvocation;

import java.lang.reflect.Method;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
public class ZPMethodBeforeAdviceInterceptor extends ZPAbstractAspectAdvice implements ZPMethodInterceptor {

    private ZPJoinPoint joinPoint;

    public ZPMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method, Object[] args, Object target) throws Throwable {
        method.invoke(target);
    }

    @Override
    public Object invoke(ZPMethodInvocation mi) throws Throwable {
        before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}
