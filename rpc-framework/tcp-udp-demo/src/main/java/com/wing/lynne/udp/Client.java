package com.wing.lynne.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws IOException {

        DatagramSocket datagramSocket = new DatagramSocket();

        byte[] data = "this is wing".getBytes("UTF-8");

        InetAddress inetAddress = InetAddress.getByName("localhost");

        DatagramPacket datagramPacket = new DatagramPacket(data,data.length,inetAddress,8080);

        datagramSocket.send(datagramPacket);

    }
}
