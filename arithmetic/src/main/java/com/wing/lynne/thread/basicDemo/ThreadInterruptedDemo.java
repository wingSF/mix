package com.wing.lynne.thread.basicDemo;

import java.util.concurrent.TimeUnit;

/**
 * 该demo用来演示线程的优雅中断方式
 */
public class ThreadInterruptedDemo {

    public static void main(String[] args) throws InterruptedException {


        Thread thread = new Thread(() -> {

            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " 死循环ing");
            }

            System.out.println(Thread.currentThread().getName() + " 将要结束");

        });

        thread.start();

        TimeUnit.SECONDS.sleep(10);

        thread.interrupt();

    }
}
