package com.interview.javabinterview.spring.aop;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/14
 */
public class OrderDao {

    public void save() {
        //int i =1/0;用于测试异常通知
        System.out.println("保存订单...");
    }
}
