package com.wing.lynne.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadRunInOrder {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {

                boolean lockResult = lock.tryLock();

                if (lockResult) {

                    try {
                        try {
                            condition1.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName());
                        condition2.signal();
                    } catch (Exception e) {

                    } finally {
                        lock.unlock();
                    }
                } else {
                    i--;
                }


            }
        }, "a").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {

                boolean lockResult = lock.tryLock();

                if (lockResult) {

                    try {
                        try {
                            condition2.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName());
                        condition3.signal();
                    } catch (Exception e) {

                    } finally {
                        lock.unlock();
                    }
                } else {
                    i--;
                }


            }
        }, "b").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {

                boolean lockResult = lock.tryLock();

                if (lockResult) {

                    try {
                        try {
                            condition3.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName());
                        condition1.signal();
                    } catch (Exception e) {

                    } finally {
                        lock.unlock();
                    }
                } else {
                    i--;
                }

            }
        }, "c").start();

        while(lock.tryLock()){
            try {
                condition1.signal();
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
            break;
        }

    }
}
