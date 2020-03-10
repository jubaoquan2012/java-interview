package com.interview.javabinterview.thread.thread_stop;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class Stop_Stop_Demo extends Thread {

    private static int i = 0;

    private static int j = 0;

    @Override
    public void run() {
        while (true) {
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
        Stop_Stop_Demo st = new Stop_Stop_Demo();
        st.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        st.stop();
        st.print();
        //多次执行结果不一致.说明stop是立即停止, run()方法中的代码不再执行
    }
}
