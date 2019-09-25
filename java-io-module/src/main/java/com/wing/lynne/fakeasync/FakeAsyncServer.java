package com.wing.lynne.fakeasync;

import com.wing.lynne.sync.SyncServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FakeAsyncServer {

    public static void main(String[] args) {

        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {
                //采用默认端口
            }
        }

        //把这里的maxPoolSize调小，可以演示下排错，然后完美的装个逼，
        FakeAsyncServerExecutePool executePool = new FakeAsyncServerExecutePool(50,3000);

        try (ServerSocket server = new ServerSocket(port)) {

            System.out.println("sync server start in port " + port);

            Socket socket = null;
            while (true) {
                socket = server.accept();
                //启动线程处理请求
                executePool.execute(new SyncServerHandler(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
