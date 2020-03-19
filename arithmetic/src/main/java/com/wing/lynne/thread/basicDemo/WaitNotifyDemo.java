package com.wing.lynne.thread.basicDemo;

import java.util.concurrent.TimeUnit;

/**
 * wait & notify 必须使用在同步场景下，等待的是同步资源对象
 * java5之后的实现类Condition类
 * Thread.join方法，底层实现就是 thread.wait(),直到线程被唤醒，状态变成不可用
 */
public class WaitNotifyDemo {

    public static void main(String[] args) throws InterruptedException {

        demo1();

        demo2();
    }

    private static void demo2() throws InterruptedException {

        Class clazz = WaitNotifyDemo.class;

        new Thread(() -> {

            synchronized (clazz) {
                System.out.println(Thread.currentThread().getName() + "进入");
                try {
                    clazz.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "退出");
            }
        }, "T1").start();


        new Thread(() -> {

            synchronized (clazz) {
                System.out.println(Thread.currentThread().getName() + "进入");
                try {
                    clazz.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "退出");
            }
        }, "T2").start();


        TimeUnit.SECONDS.sleep(3);

        synchronized (clazz){
//            clazz.notify();
            clazz.notifyAll();
        }

    }


    private static void demo1() throws InterruptedException {


        Object lock = new Object();

        Thread waitThread = new Thread(() -> {

            synchronized (lock) {

                System.out.println("wait thread hold lock going to wait");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

                System.out.println("wait thread notified but flag changed");

            }


        }, "wait-thread");

        Thread notifyThread = new Thread(() -> {

            synchronized (lock) {

                System.out.println("notify thread hold the lock going to notifyall");
                lock.notifyAll();

            }

            synchronized (lock) {
                System.out.println("notify thread get lock again ");
            }

        }, "notify-thread");

        waitThread.start();

        TimeUnit.SECONDS.sleep(5);

        notifyThread.start();

    }
}
