package com.wing.lynne.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class ServerHandle implements Runnable {

    private Socket socket;

    public ServerHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {


        //todo try with resources
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String expression;
            String result;

            while (true) {
                if ((expression = in.readLine()) == null) {
                    break;
                }
                log.info("服务端收到信息: " + expression);

                result = Calculator.cal(expression);

                out.println(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        }

    }
}
