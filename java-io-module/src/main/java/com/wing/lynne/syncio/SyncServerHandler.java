package com.wing.lynne.syncio;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SyncServerHandler implements Runnable {

    private Socket socket;

    public SyncServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
                Socket socket = this.socket;
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        ) {

            String body = "";

            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }

                System.out.println("sync server recv order :" + body);

                if ("QUERY TIME ORDER".equals(body)) {
                    out.println(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                } else {
                    out.println("BAD ORDER");
                }
            }

            System.out.println("thread goint to die");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
