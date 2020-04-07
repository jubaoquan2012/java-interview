package com.interview.javabinterview.distuibuted_socket.rmi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

import java.rmi.Naming;
import java.util.List;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/1
 */
public class RemoteClient {
    static {
        BasicConfigurator.configure();
    }

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(RemoteClient.class);

    public static void main(String[] args) throws Exception {
        // 您看，这里使用的是java名称服务技术进行的RMI接口查找。
        //RemoteServiceInterface remoteServiceInterface = (RemoteServiceInterface) Naming.lookup("rmi://192.168.61.1/queryAllUserinfo");
        RemoteServiceInterface remoteServiceInterface = (RemoteServiceInterface) Naming.lookup("rmi://127.0.0.1/queryAllUserinfo");
        List<UserInfo> users = remoteServiceInterface.queryAllUserinfo();

        RemoteClient.LOGGER.info("users.size() = " +users.size());
    }
}
