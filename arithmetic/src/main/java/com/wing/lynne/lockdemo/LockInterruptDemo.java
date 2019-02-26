package com.wing.lynne.lockdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptDemo {

    private static volatile String threadName;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Thread t1 = new Thread(() -> {
//            lock.lock();
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                threadName = Thread.currentThread().getName();
                System.out.println(Thread.currentThread().getName() + " wake up from time waiting");
            }  finally {
                System.out.println("going to unlock");
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName() + " going to die");
        }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(3);
        t1.interrupt();

    }
}
