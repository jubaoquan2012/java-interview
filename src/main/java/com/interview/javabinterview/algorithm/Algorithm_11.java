package com.interview.javabinterview.a_algorithm;

import com.interview.javabinterview.c_data_structure.LinkedList;

/**
 * 计数器
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_11 {

    private static boolean rateLimiter = false;

    //服务访问次数，可以放在Redis中，实现分布式系统的访问计数
    static Long messageCount = 0L;

    //使用LinkedList来记录滑动窗口的10个格子。
    LinkedList<Long> linkedList = new LinkedList<>();

    private void doCheck() {
        while (true) {
            linkedList.addLast(messageCount);

            if (linkedList.size() > 10) {
                linkedList.removeFirst();
            }

            //比较最后一个和第一个，两者相差一秒
            if ((linkedList.peekLast() - linkedList.peekFirst()) > 100) {
                rateLimiter = true;
                System.out.println("被限速了");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Algorithm_11 counter = new Algorithm_11();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (rateLimiter) {
                    messageCount++;
                    counter.doCheck();
                }
            }
        }).start();
    }
}
