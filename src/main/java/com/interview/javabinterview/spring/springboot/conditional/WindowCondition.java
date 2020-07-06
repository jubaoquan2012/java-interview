package com.interview.javabinterview.spring.springboot.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
public class WindowConditionServiceImpl implements ConditionService, Condition {

    @Override
    public void print() {
        System.out.println("在Window上执行");
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("os.name").contains("Windows");
    }
}
