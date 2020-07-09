package com.interview.javabinterview.proxy.pojo;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/7/7
 */
public class SafeCar extends Car {

    @Override
    public void run() {
        System.out.println("骑车启动,开始行动...........");
        super.run();
    }
}
