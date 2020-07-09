package com.interview.javabinterview.spring.aop.exam;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面类
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(com.interview.javabinterview.spring.aop.exam.Action)")
    public void annotationPointCut() {}

    @Before("execution(* com.interview.javabinterview.spring.aop.exam.DemoMethodService.*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("方法规则式拦截:" + method.getName());
    }


    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println("注解式拦截 " + action.name());
    }
}
