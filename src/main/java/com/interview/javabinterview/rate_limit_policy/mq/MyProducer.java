package com.interview.javabinterview.rate_limit_policy.mq;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public interface MyProducer {

    void sendMessage(String message);
}
