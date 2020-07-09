package com.interview.javabinterview.spring.aop;

import com.interview.javabinterview.spring.aop.intercept.ZPMethodInvocation;
import com.interview.javabinterview.spring.aop.support.ZPAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
public class ZPJdkDynamicAopProxy implements ZPAopProxy, InvocationHandler {

    private ZPAdvisedSupport advised;

    public ZPJdkDynamicAopProxy(ZPAdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, this.advised.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());
        ZPMethodInvocation invocation = new ZPMethodInvocation(proxy, this.advised.getTarget(), method, args, this.advised.getTargetClass(), chain);
        return invocation.proceed();
    }
}
