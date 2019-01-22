package com.wing.lynne.tcp;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8080); Socket accept = serverSocket.accept();

             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));

             PrintWriter printWriter = new PrintWriter(accept.getOutputStream(), true)) {


            String line = bufferedReader.readLine();

            while (line != null && !line.isEmpty()) {

                System.out.println("reve client message " + line);

//                错误的使用方式，底层的flash是个空方法
//                printWriter.write(line);
//                printWriter.flush();

                printWriter.println("reve client message [" + line + "] " + new Date(System.currentTimeMillis()));


                line = bufferedReader.readLine();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
