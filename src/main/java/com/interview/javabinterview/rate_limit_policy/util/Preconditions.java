package com.interview.javabinterview.rate_limit_policy.util;

/**
 * 先决条件参数校验
 *
 * @author Ju Baoquan
 * Created at  2020/4/3
 */
public class Preconditions {

    public static void checkArgument(boolean expression, Object message) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(message));
        }
    }
}
