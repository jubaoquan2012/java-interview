package com.interview.javabinterview.socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 服务端
 *
 * @author Ju Baoquan
 * Created at  2020/2/28
 */
public class UDPServerSocket {
    public static void main(String[] args) {
        DatagramSocket datagramSocket = null;
        try {
            //创建服务,并且接收一个服务
            datagramSocket = new DatagramSocket(8080);
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            datagramSocket.receive(receivePacket);
            System.out.println(new String(receiveData, 0, receiveData.length));
        } catch (Exception e) {

        } finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }
}
