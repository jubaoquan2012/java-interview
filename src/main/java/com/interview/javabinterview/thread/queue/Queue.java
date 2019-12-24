package com.interview.javabinterview.thread.queue;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface Queue<E> extends Collection<E> {

    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();
}
