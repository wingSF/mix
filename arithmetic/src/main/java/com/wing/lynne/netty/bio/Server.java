package com.wing.lynne.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 模拟 BIO server
 */
@Slf4j
public class Server {

    //默认端口号
    private static int DEFAULT_PORT = 7777;

    //单例ServerSocket
    private static ServerSocket serverSocket;

    //服务的启动方法
    public static void start() throws IOException {

        start(DEFAULT_PORT);

    }

    public synchronized static void start(int port) throws IOException {

        if (serverSocket != null) {
            return;
        }

        //todo try with resources on class field
        try (
                ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)
        ) {
            log.info("服务端已启动，端口号 " + port);

            while (true) {

                Socket socket = serverSocket.accept();

                new Thread(new ServerHandle(socket)).start();


            }

        }

    }

}
