package com.wing.lynne.thread.lockAndSync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 为了演示lock与sync对与interrupt的响应
 * 但是没有得到结果
 * todo
 *
 */
public class InterruptSynchronizedThread {

    public static void main(String[] args) throws InterruptedException {

        final Object object = new Object();


        Thread syncThread1 = new Thread(() -> {

            synchronized (object){
                while(!Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread().getName()+" sleeping ");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, "syncThread1");

        Thread syncThread2 = new Thread(() -> {

            synchronized (object){
                while(!Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread().getName()+" sleeping ");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, "syncThread2");

        Lock lock = new ReentrantLock();

        Thread lockThread1= new Thread(()->{

            lock.lock();

            while(!Thread.currentThread().isInterrupted()){
                System.out.println(Thread.currentThread().getName()+" sleeping ");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        },"lockThread1");

        Thread lockThread2 = new Thread(()->{

            lock.lock();

            while(!Thread.currentThread().isInterrupted()){
                System.out.println(Thread.currentThread().getName()+" sleeping ");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        },"lockThread2");

        syncThread1.start();
        syncThread2.start();
        lockThread1.start();
        lockThread2.start();


        TimeUnit.SECONDS.sleep(10);

        syncThread1.interrupt();
        lockThread1.interrupt();
    }
}
