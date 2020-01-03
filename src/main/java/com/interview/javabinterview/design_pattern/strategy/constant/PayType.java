package com.interview.javabinterview.design_pattern.strategy.constant;


import com.interview.javabinterview.design_pattern.strategy.service.AliPay;
import com.interview.javabinterview.design_pattern.strategy.service.JDPay;
import com.interview.javabinterview.design_pattern.strategy.service.Payment;
import com.interview.javabinterview.design_pattern.strategy.service.WeChatPay;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/13
 */
public enum  PayType {

    Ali_Pay(new AliPay()),

    JD_Pay(new JDPay()),

    WeChat_Pay(new WeChatPay());

    private Payment payment;

    PayType(Payment payment) {
        this.payment = payment;
    }

    public Payment getPayment(){
        return this.payment;
    }
}
