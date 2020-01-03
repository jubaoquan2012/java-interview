package com.interview.javabinterview.threadstudy.threadwait;

/**
 * 模拟生产和消费的例子
 *
 * @author Ju Baoquan
 * Created at  2019/10/17
 */
public class ThreadWaitDemo1 {

    private int MAX_SIZE;

    public class MyQueue implements Runnable {

        private int size;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public void run() {

        }
    }

    /**
     * sleep : 不释放锁,让出CPU资源
     * wait :释放当前的对象锁,让出CPU资源,使得当前线程进入阻塞;
     *
     * @param queue
     */
    public synchronized void producer(MyQueue queue) {
        //消费队列满,则等待队列空闲
        while (queue.size == MAX_SIZE) {
            try {
                //挂起当前线程,并释放通过同步块获取的queue上的锁,让消费者线程可以获取该锁,
                //然后获取队列里面的元素
                queue.wait();//释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //空闲则生成元素,并通知消费者线程
        // queue.add(ele);
        // queue.notifyAll;
    }

    public synchronized void consumer(MyQueue queue) {
        //消费队列为空
        while (queue.size == 0) {
            try {
                //挂起当前线程,并释放通过同步块获取的queue上的锁,让生产者线程可以获取该锁,
                //然后获取队列里面的元素
                queue.wait();//释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //消费元素,并通知唤醒生产者线程
    //queue.take();
    //queue.notifyAll();
}
