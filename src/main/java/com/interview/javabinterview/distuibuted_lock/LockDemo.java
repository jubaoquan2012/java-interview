package com.interview.javabinterview.distuibuted_lock;

import com.interview.javabinterview.distuibuted_lock.zookeeper.ZK_ExclusiveLock;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/27
 */
public class LockDemo {

    private static volatile boolean checkTicket = true;
    private static volatile int ticket = 0;

    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        TicketSeller ticketSeller = new TicketSeller();

        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        try {
                            if (!checkTicket) {
                                System.out.println("ticket:"+ticket);
                                break;
                            }
                            checkTicket = ticketSeller.sellTicketWithLock();
                            ticket++;
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }

    }
}
