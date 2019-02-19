package com.wing.lynne.lockdemo;

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

            result = items[currentIndex];
            currentIndex--;

            isFull.signal();
        } finally {
            reentrantLock.unlock();
        }


        return result;
    }
}
