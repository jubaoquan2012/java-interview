package com.interview.javabinterview.thread.thread_stop;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class Stop_interrupt_Demo extends Thread {

    private static int i = 0;

    private static int j = 0;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
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
        Stop_interrupt_Demo st = new Stop_interrupt_Demo();
        st.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        st.interrupt();//设置中断标志位true
        st.print();
    }
}
