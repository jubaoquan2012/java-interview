package com.interview.javabinterview.design_pattern.proxy;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/9/16
 */
public class Student implements Person {

    @Override
    public String getName() {
        System.out.println("鞠保权");
        return "jubaoquan";
    }

    @Override
    public int getAge() {
        System.out.println("29岁");
        return 29;
    }
}
