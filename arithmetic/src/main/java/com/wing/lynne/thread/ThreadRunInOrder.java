package com.wing.lynne.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadRunInOrder {

    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        int printTime = 10;

        new Thread(new Print(lock, condition1, condition2, printTime), "a").start();
        new Thread(new Print(lock, condition2, condition3, printTime), "b").start();
        new Thread(new Print(lock, condition3, condition1, printTime), "c").start();

        //这段代码用来获取第一个a线程对象
        Thread aThread = null;
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().equals("a")) {
                aThread = thread;
                break;
            }
        }

        //判断a线程对象的状态是等待，再执行唤醒，避免main执行signal的时候，a线程还没有出于wait状态，出现上述情况，程序会假死
        while (aThread.getState() != Thread.State.WAITING) {
            TimeUnit.SECONDS.sleep(1);
        }

        while (lock.tryLock()) {
            try {
                condition1.signal();
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
            break;
        }

    }

    static class Print implements Runnable {

        private Lock lock;
        private Condition waitCondition;
        private Condition signalCondition;
        private int printTime;

        public Print(Lock lock, Condition waitCondition, Condition signalCondition, int printTime) {
            this.lock = lock;
            this.waitCondition = waitCondition;
            this.signalCondition = signalCondition;
            this.printTime = printTime;
        }

        @Override
        public void run() {
            for (int i = 0; i < printTime; i++) {

                boolean lockResult = lock.tryLock();

                if (lockResult) {

                    try {
                        try {
                            waitCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName());
                        signalCondition.signal();
                    } catch (Exception e) {

                    } finally {
                        lock.unlock();
                    }
                } else {
                    i--;
                }

            }
        }
    }
}
