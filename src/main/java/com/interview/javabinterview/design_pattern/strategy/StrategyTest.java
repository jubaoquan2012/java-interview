package com.interview.javabinterview.design_pattern.strategy;


import com.interview.javabinterview.design_pattern.strategy.bo.Order;
import com.interview.javabinterview.design_pattern.strategy.constant.PayType;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/13
 */
public class StrategyTest {

    public static void main(String[] args) {
        Order order = new Order("1", "201908130009", 1314.52);

        System.out.println(order.pay(PayType.JD_Pay));
        System.out.println(order.pay(PayType.Ali_Pay));
        //System.out.println(order.pay(PayType.WeChat_Pay));
    }
}
