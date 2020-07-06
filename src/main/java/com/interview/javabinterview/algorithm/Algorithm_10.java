package com.interview.javabinterview.a_algorithm;

import java.util.Stack;

/**
 * 最小栈
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_10 {

    public static void main(String[] args) {
        System.out.println("");
    }

    /** initialize your data structure here. */
    Stack<Integer> stack;

    Stack<Integer> minStack;

    public Algorithm_10() {
        this.stack = new Stack<Integer>();
        this.minStack = new Stack<Integer>();
    }

    public void push(int x) {
        stack.push(x);
        if (minStack.empty()) {
            minStack.push(x);
        } else if (minStack.peek() >= x) {
            minStack.push(x);
        }
    }

    public void pop() {
        if (stack.empty()) {
            return;
        }
        int pop = stack.pop();
        if (!minStack.empty() && minStack.peek() == pop) {
            minStack.pop();
        }
    }

    public int top() {
        if (stack.empty()) {
            return 0;
        }
        return stack.peek();
    }

    public int getMin() {
        if (minStack.empty()) {
            return 0;
        }
        return minStack.peek();
    }
}
