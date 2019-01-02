package com.wing.lynne.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo2 {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static int i = 0;

    public static void main(String[] args) {

        List<Thread> threadList = new ArrayList();

        for (int i = 0; i < 100; i++) {

            Thread thread = new Thread(() -> {

                for (int j = 0; j < 10000; j++) {
                    add();
                    safeAdd();
                }
            });

            threadList.add(thread);

        }

        for (Thread thread : threadList) {
            thread.start();
        }

        System.out.println("i = " + i);
        System.out.println("atomicInteger = " + atomicInteger);


    }

    //循环+cas实现的原子操作
    private static void safeAdd() {
        while (true) {
            int i = atomicInteger.get();
            boolean b = atomicInteger.compareAndSet(i, ++i);
            if (b) {
                break;
            }
        }
    }

    //非原子操作的递增
    private static void add() {
        i++;
    }


}
