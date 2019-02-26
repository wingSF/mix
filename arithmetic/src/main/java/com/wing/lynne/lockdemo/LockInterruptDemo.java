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

                threadName = Thread.currentThread().getName();
                System.out.println(Thread.currentThread().getName() + " wake up from time waiting");
            } catch (Exception e) {

                System.out.println(Thread.currentThread().getName() + " catch exception");

            } finally {


            }

            System.out.println(Thread.currentThread().getName() + " going to die");
        }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(3);
        t1.interrupt();

    }

}

/**
 * 总结
 *  lock.lock不响应中断
 *  lock.lockInterruptibly会响应中断，并从当前行抛出一个中断异常，后续代码根据异常来进行特殊处理
 */
