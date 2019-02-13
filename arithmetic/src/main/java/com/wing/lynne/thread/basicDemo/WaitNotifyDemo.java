package com.wing.lynne.thread.basicDemo;

import java.util.concurrent.TimeUnit;

public class WaitNotifyDemo {

    static Object lock = new Object();
    static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        Thread waitThread = new Thread(() -> {

            synchronized (lock){
                while(flag){

                    System.out.println("wait thread hold lock going to wait");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                System.out.println("wait thread notified but flag changed");

            }


        }, "wait-thread");

        Thread notifyThread = new Thread(() -> {

            synchronized (lock){

                while(flag){

                    System.out.println("notify thread hold the lock going to notifyall");
                    lock.notifyAll();
                    flag = false;
                }

            }

            synchronized (lock){
                System.out.println("notify thread get lock again ");
            }

        }, "notify-thread");

        waitThread.start();

        TimeUnit.SECONDS.sleep(5);

        notifyThread.start();

    }
}
