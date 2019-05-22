package com.wing.lynne.thread.lockAndSync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 为了演示lock与sync对与interrupt的响应
 * <p>
 * 以下代码演示了synchronized和lock在遇到InterruptException时的处理机制
 * <p>
 * synchronize会自动释放锁
 * lock必须手动调用unlock方法，才能释放
 */
public class InterruptSynchronizedThread {

    public static void main(String[] args) throws InterruptedException {

//        reactForException();

        reactForInterrupt();
        return;

    }

    private static void reactForInterrupt() throws InterruptedException {

        final Object object = new Object();

        Thread syncThread1 = new Thread(() -> {
            synchronized (object) {
                while (true) {
                    try {
                        System.out.println(Thread.currentThread() + " 获取同步对象成功");
                        TimeUnit.HOURS.sleep(1);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }, "syncThread1");

        Thread syncThread2 = new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread() + " 获取同步对象成功");
            }
        }, "syncThread2");

        Lock lock = new ReentrantLock();

        Thread lockThread1 = new Thread(() -> {

            lock.lock();

            try {
                System.out.println(Thread.currentThread() + " 获取锁对象成功");
                while (true) {
                    TimeUnit.HOURS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "lockThread1");

        Thread lockThread2 = new Thread(() -> {

//            此处使用lock.lock()将不会响应中断，使用lock.lockInterruptibly()则会抛出中断异常，伺机处理
            lock.lock();
//            try {
//                lock.lockInterruptibly();
//            } catch (InterruptedException e) {
//                System.out.println(Thread.currentThread()+" 获取锁失败");
//                return;
//            }
            try {
                System.out.println(Thread.currentThread() + " 获取锁对象成功 ");
            } finally {
                lock.unlock();
            }


        }, "lockThread2");

        syncThread1.start();
        lockThread1.start();
        syncThread2.start();
        lockThread2.start();


        TimeUnit.SECONDS.sleep(10);

        syncThread2.interrupt();
        lockThread2.interrupt();

    }

    private static void reactForException() throws InterruptedException {
        final Object object = new Object();

        Thread syncThread1 = new Thread(() -> {

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + " sleeping ");

                        if (System.currentTimeMillis() % 2 == 0) {
                            throw new InterruptedException();
                        }
                    }
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "syncThread1");

        Thread syncThread2 = new Thread(() -> {

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + " sleeping ");
                    }
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "syncThread2");

        Lock lock = new ReentrantLock();

        Thread lockThread1 = new Thread(() -> {

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    //注意lock.lock()方法应该放置在try-catch-finally块外边，防止获取锁异常时，导致被解锁，此处只是为了演示
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " sleeping ");

                    if (System.currentTimeMillis() % 2 == 0) {
                        throw new InterruptedException();
                    }

                    //当发生中断异常的时候，不会调用下面这个unlock(),所以锁没有释放，导致lockThread2线程无法获取锁
                    lock.unlock();
                    //另注，这是一种错误的写法，只是为了达到演示的效果，正确的应该将unlock语句放到finally代码块中执行，可参见lockThread2的unlock调用
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }, "lockThread1");

        Thread lockThread2 = new Thread(() -> {


            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " sleeping ");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }


        }, "lockThread2");

        syncThread1.start();
        syncThread2.start();
        lockThread1.start();
        lockThread2.start();


        TimeUnit.SECONDS.sleep(10);
    }
}
