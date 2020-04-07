package com.interview.javabinterview.algorithm.byte_dance;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class Demo {

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-4);
        minStack.push(0);

        minStack.push(-2);
        minStack.push(-3);

        System.out.println(minStack.top());         //-3
        System.out.println(minStack.getMin());      //-4
        minStack.pop();
        System.out.println(minStack.top());         //-4
        System.out.println(minStack.getMin());      //-4
    }
}
