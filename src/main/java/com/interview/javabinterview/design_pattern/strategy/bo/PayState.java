package com.interview.javabinterview.design_pattern.strategy.bo;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/8/13
 */
public class PayState {

    private int code;

    private Object data;

    private String message;

    public PayState(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    @Override
    public String toString() {
        return "交易成功 {" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
