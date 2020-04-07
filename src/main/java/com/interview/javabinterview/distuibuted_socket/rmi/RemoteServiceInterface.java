package com.interview.javabinterview.distuibuted_socket.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 接口
 *
 * @author Ju Baoquan
 * Created at  2020/4/1
 */
public interface RemoteServiceInterface extends Remote {

    public List<UserInfo> queryAllUserinfo() throws RemoteException;
}
