package com.interview.javabinterview.algorithm;

/**
 * Description:
 *
 * @author baoquan.Ju
 * Created at  2020/12/11
 */
public class Algorithm_22 {

    private volatile int count;

    private final static String[] outStr = new String[]{"a", "b", "c"};

    public static void main(String[] args) {
        Algorithm_22 algorithm_22 = new Algorithm_22();
        algorithm_22.start();
    }

    private void start() {
        Thread threadA = new Thread(new ThreadPint(),"thread_1");
        Thread threadB = new Thread(new ThreadPint(),"thread_2");
        Thread threadC = new Thread(new ThreadPint(),"thread_3");
        threadA.start();
        threadB.start();
        threadC.start();
    }

    public class ThreadPint implements Runnable {
        @Override
        public void run() {
            synchronized (Algorithm_22.this) {
                while (count <= 50) {
                    System.out.println(outStr[count % 3]+":"+Thread.currentThread().getName());
                    count++;
                }
                Algorithm_22.this.notify();
                try {
                    Algorithm_22.this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
