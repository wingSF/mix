package com.wing.lynne.tcp;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("127.0.0.1", 8080);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        ) {

            String line = bufferedReader.readLine();

            while (!"bye".equals(line)) {

                printWriter.println(line);

                System.out.println("reve response " + bufferedReader1.readLine());

                line = bufferedReader.readLine();

            }

        } catch (Exception e) {

        }

    }
}
