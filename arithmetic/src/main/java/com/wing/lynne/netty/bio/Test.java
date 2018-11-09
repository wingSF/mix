package com.wing.lynne.netty.bio;

import java.io.IOException;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(100);

        char[] op = {'+', '-', '*', '/'};

        final Random random = new Random(System.currentTimeMillis());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    String expression = random.nextInt(10)+""+op[random.nextInt(4)]+(random.nextInt(10)+1);

                    try {
                        Client.send(expression);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }
}
