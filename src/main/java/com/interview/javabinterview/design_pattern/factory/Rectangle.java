package com.interview.javabinterview.design_pattern.factory;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
