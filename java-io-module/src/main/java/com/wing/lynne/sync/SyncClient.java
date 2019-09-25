package com.wing.lynne.sync;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SyncClient {

    public static void main(String[] args) {

        //客户端端口
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        try (
                Socket socket = new Socket("127.0.0.1", port);
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {

            out.println("QUERY TIME ORDER");

            System.out.println();

            String response = in.readLine();

            System.out.println("get response now is :" + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
