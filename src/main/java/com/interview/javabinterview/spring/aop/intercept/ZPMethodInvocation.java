package com.interview.javabinterview.spring.aop.intercept;

import com.interview.javabinterview.spring.aop.ZPJoinPoint;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
public class ZPMethodInvocation implements ZPJoinPoint {

    private final Object proxy;

    private final Object target;

    private final Method method;

    private Object[] arguments = new Object[0];

    private final Class<?> targetClass;

    protected final List<?> interceptorsAndDynamicMethodMatchers;

    private int currentInterceptorIndex = -1;

    public ZPMethodInvocation(
            Object proxy, Object target, Method method, Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(this.target, this.arguments);
        }

        /**这里沿着定义好的interceptorOrInterceptionAdvice链进行处理*/
        Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        if (interceptorOrInterceptionAdvice instanceof ZPMethodInterceptor) {
            ZPMethodInterceptor dm =
                    (ZPMethodInterceptor) interceptorOrInterceptionAdvice;
            return dm.invoke(this);
        } else {
            //动态匹配失败时,略过当前Interceptor,调用下一个Interceptor
            return proceed();
        }
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }
}
