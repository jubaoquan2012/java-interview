package com.interview.javabinterview.algorithm.database;

/**
 * 双向链表
 *
 * @author Ju Baoquan
 * Created at  2020/6/9
 */
public class DoubleNodeCodeJu {

    public int value;

    public DoubleNodeCodeJu pre;

    public DoubleNodeCodeJu next;

    public DoubleNodeCodeJu(int value) {
        this.value = value;
    }

    public DoubleNodeCodeJu(DoubleNodeCodeJu pre, int value) {
        this.pre = pre;
        this.value = value;
    }
}
