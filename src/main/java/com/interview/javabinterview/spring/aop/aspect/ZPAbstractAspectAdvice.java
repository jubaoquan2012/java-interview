package com.interview.javabinterview.spring.aop.aspect;

import com.interview.javabinterview.spring.aop.ZPJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * &#x7c7b;
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
public abstract class ZPAbstractAspectAdvice {

    private Method aspectMethod;

    private Object aspectTarget;

    public ZPAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    public void setThrowName(String throwName) {

    }

    protected Object invokeAdviceMethod(ZPJoinPoint joinPoint, Object returnValue, Throwable tx) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();
        if (paramTypes == null || paramTypes.length == 0) {
            return this.aspectMethod.invoke(aspectTarget);
        } else {
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                if (paramTypes[i] == ZPJoinPoint.class) {
                    args[i] = joinPoint;
                } else if (paramTypes[i] == Throwable.class) {
                    args[i] = tx;
                } else if (paramTypes[i] == Object.class) {
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }
}
