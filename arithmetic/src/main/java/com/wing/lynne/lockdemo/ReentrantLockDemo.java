package com.wing.lynne.lockdemo;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    static int a = 0;

    static ReentrantLock reentrantLock = new ReentrantLock();

    public static void write() {

        reentrantLock.lock();

        try {

            a++;

        } finally {

            reentrantLock.unlock();
        }

    }

    public static void read() {

        reentrantLock.lock();

        try {
            int i = a;
            System.out.println("currnet a is " + i);
        } finally {
            reentrantLock.unlock();
        }

    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            new Thread(() -> {
                write();
            }).start();

        }

        //可以将此处的等待，改为CompletableFuture,来等待线程执行结束
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        read();


    }
}
