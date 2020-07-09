package com.interview.javabinterview.proxy.pojo;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/7/6
 */
public class Dog implements Animal {

    @Override
    public void run() {
        System.out.println("狗....run");
    }

    @Override
    public void eat(String food) {
        System.out.println("狗....eat:" + food);
    }
}
