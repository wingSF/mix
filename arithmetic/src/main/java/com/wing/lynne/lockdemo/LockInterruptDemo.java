package com.wing.lynne.lockdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptDemo {

    private static volatile String threadName;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Thread t1 = new Thread(() -> {

            try {
                testMethod(lock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(3);
        t1.interrupt();

    }

    public static void testMethod(ReentrantLock lock) throws InterruptedException {
        //            lock.lock();
        lock.lockInterruptibly();

        try {
            threadName = Thread.currentThread().getName();
        } finally {
            lock.unlock();
        }
    }

}

/**
 * 总结
 *  lock.lock不响应中断
 *  lock.lockInterruptibly会响应中断，并从当前行抛出一个中断异常，后续代码根据异常来进行特殊处理
 */
