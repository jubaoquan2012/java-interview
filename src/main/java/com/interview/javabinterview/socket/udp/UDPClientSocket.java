package com.interview.javabinterview.socket.udp;

import java.io.IOException;
import java.net.*;

/**
 * 客户端
 *
 * @author Ju Baoquan
 * Created at  2020/2/28
 */
public class UDPClientSocket {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = null;
        try {
            InetAddress address = InetAddress.getByName("localhost");
            byte[] sendData = "Hello,jubaoquan".getBytes();
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, address, 8080);
            datagramSocket = new DatagramSocket();
            datagramSocket.send(sendPacket);
        } catch (Exception e) {

        } finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }

    }
}
