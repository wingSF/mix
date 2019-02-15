package com.wing.lynne.lockdemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyTwinsLock implements Lock {

    private final Sync sync = new Sync(2);


    @Override
    public void lock() {
        sync.acquireShared(1);
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    private static final class Sync extends AbstractQueuedSynchronizer {

        Sync(int count) {
            if (count <= 0) {
                throw new RuntimeException();
            }

            setState(count);
        }

        @Override
        public int tryAcquireShared(int reduceCount) {
            for (; ; ) {

                int current = getState();
                int newCount = current - reduceCount;
                //如果newCount<0,直接返回小于0的值
                //如果newCount>=0,
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        public boolean tryReleaseShared(int returnCount) {
            for (; ; ) {

                int current = getState();
                int newCount = current + returnCount;

                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final MyTwinsLock myTwinsLock = new MyTwinsLock();

        CountDownLatch countDownLatch = new CountDownLatch(10);


        for (int i = 0; i < 10; i++) {

            Thread worker = new Thread(() -> {

                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (true) {
                    myTwinsLock.lock();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        myTwinsLock.unlock();
                    }
                }

            });

            worker.setDaemon(true);
            worker.start();

            countDownLatch.countDown();
        }


        for (int i = 0; i < 10; i++) {

            TimeUnit.SECONDS.sleep(1);
            System.out.println();

        }

    }
}
