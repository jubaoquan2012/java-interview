package com.interview.javabinterview.spring.aop.exam;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 使用注解被拦截的类
 *
 * @author Ju Baoquan
 * Created at  2020/5/25
 */
@Service
public class DemoAnnotationService {

    @Action(name = "注解式拦截的add操作")
    public void add() {

    }
}
