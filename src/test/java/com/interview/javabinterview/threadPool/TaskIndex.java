package com.interview.javabinterview.threadPool;

/**
 * @author shineng.he
 * createTime 2019/4/23 16:44
 */
public class TaskIndex {

    // 添加任务启动的序列标志
    public int addTaskIndex = 0;
    // 所有任务，被线程执行完，恢复到最初的核心线程数，任务执行完结束标志
    // 除了刚开始启动的状态 到核心线程刚加满
    public int taskStopFlag = 0;

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }
}
