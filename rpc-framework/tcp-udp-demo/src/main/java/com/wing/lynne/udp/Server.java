package com.wing.lynne.udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

    public static void main(String[] args) throws IOException {

        DatagramSocket datagramSocket = new DatagramSocket(8080);

        byte[] data = new byte[1024];

        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);

        datagramSocket.receive(datagramPacket);

        System.out.println(new String(data));

    }
}
