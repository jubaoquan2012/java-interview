package com.interview.javabinterview.thread.thread_stop;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class Stop_flag_Demo extends Thread {

    private static int i = 0;

    private static int j = 0;

    private static volatile boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            i++;
            for (int i = 0; i < 1000; i++) {

            }
            j++;
        }
    }

    private static void print() {
        System.out.println("i = " + i + "\nj= " + j);
    }

    public static void main(String[] args) {
        Stop_flag_Demo st = new Stop_flag_Demo();
        st.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = false;
        st.print();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
