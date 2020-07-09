package com.interview.javabinterview.dubbo.spi;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/3
 */
public class PrintServiceImpl implements PrintService{

    @Override
    public void printInfo() {
        System.out.println("helloWorld");
    }
}
