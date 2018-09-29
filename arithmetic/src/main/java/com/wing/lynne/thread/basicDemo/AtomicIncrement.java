package com.wing.lynne.thread.basicDemo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用java.util.concurrent包下的AtomicInteger下的方法实现自增
 *
 * AtomicInteger#incrementAndGet方法
 *  通过cas（compare and swap）实现了原子递增
 *  即将a++的三个步骤，做到了线程安全
 *
 */
public class AtomicIncrement {

    private static volatile AtomicInteger a = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                a.incrementAndGet();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                a.incrementAndGet();
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                a.incrementAndGet();
            }
        });
        Thread thread4 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                a.incrementAndGet();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println("a = " + a);

    }
}
