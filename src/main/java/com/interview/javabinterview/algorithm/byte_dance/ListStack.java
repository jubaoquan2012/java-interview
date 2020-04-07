package com.interview.javabinterview.algorithm.byte_dance;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class ListStack<E> {

    private List<E> stack;

    public ListStack() {
        this.stack = new ArrayList<>();
    }

    public void push(E e) {
        stack.add(e);
    }

    public E pop() {
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        E pop = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        return pop;
    }

    public E top() {
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    public boolean empty(){
        if (stack == null || stack.isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
