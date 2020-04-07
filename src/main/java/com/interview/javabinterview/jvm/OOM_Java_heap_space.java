package com.interview.javabinterview.jvm;

/**
 * 内存溢出: java.lang.OutOfMemoryError: Java heap space
 * VM Args:-Xmx12m -XX:+PrintGCDetails
 * 调整VM:
 * VM Args:-Xmx13m -XX:+PrintGCDetails
 * <p>
 * 因为 默认新老代1:2 即4 : 8M 都装不下数组，
 * JVM 进程除了分配数组大小，还有指向类(数组中元素对应的类)信息的指针、锁信息等，
 * 实际需要的堆空间是可能超过 12M 的， 12M 也只是尝试出来的值，不同的机器可能不一样
 *
 * @author Ju Baoquan
 * Created at  2020/3/25
 */
public class OOM_Java_heap_space {

    static final int SIZE = 2 * 1024 * 1024;

    public static void main(String[] a) {
        int[] i = new int[SIZE];
        System.out.println("test");
    }
}
