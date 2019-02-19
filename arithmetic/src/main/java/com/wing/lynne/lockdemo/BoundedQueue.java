package com.wing.lynne.lockdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue {

    private Object[] items;
    private int currentIndex;

    private final ReentrantLock reentrantLock = new ReentrantLock();

    private final Condition isEmpty = reentrantLock.newCondition();
    private final Condition isFull = reentrantLock.newCondition();


    public BoundedQueue(int length) {
        items = new Object[length];
    }

    public void add(Object object) throws InterruptedException {

        reentrantLock.lock();
        try {

            while (currentIndex == items.length) {
                isFull.await();
            }

            items[currentIndex] = object;
            currentIndex++;

            isEmpty.signal();
        } finally {
            reentrantLock.unlock();
        }

    }

    public Object remove() throws InterruptedException {

        reentrantLock.lock();

        Object result = null;
        try {

            while (currentIndex == 0) {
                isEmpty.await();
            }

            result = items[currentIndex - 1];
            currentIndex--;

            isFull.signal();
        } finally {
            reentrantLock.unlock();
        }


        return result;
    }

    public static void main(String[] args) throws InterruptedException {

        BoundedQueue boundedQueue = new BoundedQueue(10);

        for (int i = 0; i < 11; i++) {
            new Thread(() -> {
                try {
                    boundedQueue.add(10);
                    System.out.println(Thread.currentThread().getName() + "添加成功");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(5);

        boundedQueue.remove();

        for (int i = 0; i < 11; i++) {
            new Thread(() -> {
                try {
                    boundedQueue.remove();
                    System.out.println(Thread.currentThread().getName() + "移除成功");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(5);

        boundedQueue.add(10);

    }
}
