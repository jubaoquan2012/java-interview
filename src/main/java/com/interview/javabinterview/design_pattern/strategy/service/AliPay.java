package com.interview.javabinterview.design_pattern.strategy.service;


import com.interview.javabinterview.design_pattern.strategy.bo.PayState;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/13
 */
public class AliPay implements Payment {
    @Override
    public PayState pay(String uid, double amount) {
        System.out.println("支付宝支付成功"+amount);
        return new PayState(200,amount,"支付成功");
    }
}
