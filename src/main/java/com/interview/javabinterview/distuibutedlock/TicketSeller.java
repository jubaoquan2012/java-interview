package com.interview.javabinterview.distuibutedlock;

import com.interview.javabinterview.distuibutedlock.zookeeper.ZK_ExclusiveLock;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/27
 */
public class TicketSeller {

    private int count = 1000;

    private boolean sell() {
        boolean result = checkTicket();
        System.out.println("售票开始,余票:" + count);
        if (result){
            count--;
        }
        return result;
    }

    public boolean sellTicketWithLock() throws KeeperException, InterruptedException, IOException {
        ZK_ExclusiveLock lock = new ZK_ExclusiveLock();
        try {
            lock.acquireLock();
            if (!checkTicket()) {
                return false;
            }
            return sell();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.releaseLock();
        }
        return false;
    }

    private boolean checkTicket() {
        if (count > 0) {
            return true;
        } else {
            System.out.println("没票了");
            return false;
        }
    }
}
