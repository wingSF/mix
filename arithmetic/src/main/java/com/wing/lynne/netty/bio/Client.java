package com.wing.lynne.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class Client {

    //服务端的port
    private static int DEFAULT_SERVER_PORT = 7777;
    //服务端的ip
    private static String DEFAULT_SERVER_IP = "127.0.0.1";

    public static void send(String expression) throws IOException {

        send(DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT, expression);

    }

    private static void send(String defaultServerIp, int defaultServerPort, String expression) throws IOException {

        log.info("expression " + expression);

        try (
            Socket socket = new Socket(defaultServerIp,defaultServerPort);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream())
        ){

        }

    }


}
