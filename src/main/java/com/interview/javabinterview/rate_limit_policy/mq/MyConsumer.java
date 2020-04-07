package com.interview.javabinterview.rate_limit_policy.mq;

import java.util.function.Consumer;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public interface MyConsumer {

    void consume(Consumer<String> callback);
}
