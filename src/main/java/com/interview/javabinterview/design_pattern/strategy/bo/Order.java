package com.interview.javabinterview.design_pattern.strategy.bo;


import com.interview.javabinterview.design_pattern.strategy.constant.PayType;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/13
 */
public class Order {

    private String uid;

    private String orderId;

    private double amount;


    public Order(String uid, String orderId, double amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    public PayState pay(PayType payType) {
        return payType.getPayment().pay(this.uid,this.amount);
    }
}
