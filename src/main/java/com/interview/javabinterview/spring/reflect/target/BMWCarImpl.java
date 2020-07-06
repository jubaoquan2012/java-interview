package com.interview.javabinterview.spring.reflect.target;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/22
 */
@CarAnnotation
public class CarImpl implements Car {

    @Override
    public void msg() {
        System.out.println("我的第一个注解实现类");
    }
}
