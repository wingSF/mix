package com.wing.lynne.thread.basicDemo;

import java.util.concurrent.TimeUnit;

public class StopThreadByInterrupt {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {

            int count = 0;

            while (!Thread.currentThread().isInterrupted()) {

                count++;
            }

            System.out.println("number is " + count);
        }, "myThread");

        thread.start();

        TimeUnit.SECONDS.sleep(1);

        thread.interrupt();

    }
}
