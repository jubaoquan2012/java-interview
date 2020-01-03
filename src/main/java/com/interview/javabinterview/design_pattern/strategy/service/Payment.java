package com.interview.javabinterview.design_pattern.strategy.service;


import com.interview.javabinterview.design_pattern.strategy.bo.PayState;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/13
 */
public interface Payment {

    PayState pay(String uid, double amount);
}
