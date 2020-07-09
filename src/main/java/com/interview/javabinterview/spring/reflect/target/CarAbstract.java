package com.interview.javabinterview.spring.reflect.target;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/22
 */
public abstract class CarAbstract implements Car {

    public void stop() {
        System.out.println("都会自动停车");
    }

    @Override
    public void run(int speed) {
        System.out.println("此车最高时速是:" + speed);
    }
}
