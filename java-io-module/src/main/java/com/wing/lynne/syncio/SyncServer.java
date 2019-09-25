package com.wing.lynne.syncio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步模型的服务器端
 */
public class SyncServer {

    public static void main(String[] args) {

        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {
                //采用默认端口
            }
        }

        try (ServerSocket server = new ServerSocket(port)) {

            System.out.println("sync server start in port " + port);

            Socket socket = null;
            while (true) {
                socket = server.accept();
                //启动线程处理请求
                new Thread(new SyncServerHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
