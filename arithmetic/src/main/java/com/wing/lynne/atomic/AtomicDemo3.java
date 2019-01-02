package com.wing.lynne.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicDemo3 {

    public static void main(String[] args) throws InterruptedException {

        //通过cas更新账户余额，模拟aba问题
        compareAndSwapBug();

        System.out.println("======================================================");

        //通过AtomicStampReference解决aba问题
        compareAndSwapWithAtomicStampReference();


    }

    /**
     * 使用带版本的cas操作，避免上aba的问题
     * @throws InterruptedException
     */
    private static void compareAndSwapWithAtomicStampReference() throws InterruptedException {

        Integer accountValue = new Integer(200);
        int version = 0;

        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(accountValue, version);

        /**
         * take线程用来模拟取钱100，即200变成100的操作
         */
        Thread takeMoney1 = new Thread(() -> {
            Integer reference = atomicStampedReference.getReference();
            boolean result = atomicStampedReference.compareAndSet(reference, reference - 100, version, version + 1);
            System.out.println("first take result " + result);
        });

        Thread takeMoney2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                Integer reference = atomicStampedReference.getReference();
                int stamp = atomicStampedReference.getStamp();
                boolean result = atomicStampedReference.compareAndSet(reference, reference - 100, version, version + 1);
                System.out.println("second take result " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        /**
         * 该线程用来模拟朋友转账100
         */
        Thread addMoney = new Thread(() -> {

            Integer reference = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            boolean result = atomicStampedReference.compareAndSet(reference, reference + 100, stamp, stamp + 1);
            System.out.println("first add result " + result);

        });


        takeMoney1.start();
        takeMoney2.start();
        addMoney.start();

        takeMoney1.join();
        takeMoney2.join();
        addMoney.join();

        System.out.println("method compareAndSwapWithAtomicStampReference result is " + accountValue);


    }

    /**
     * cas操作，模拟发生aba的问题
     * @throws InterruptedException
     */
    private static void compareAndSwapBug() throws InterruptedException {

        AtomicInteger accountValue = new AtomicInteger(200);

        /**
         * take线程用来模拟取钱100，即200变成100的操作
         */
        Thread takeMoney1 = new Thread(() -> {
            boolean result = accountValue.compareAndSet(200, 100);
            System.out.println("first take result " + result);
        });

        Thread takeMoney2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                boolean result = accountValue.compareAndSet(200, 100);
                System.out.println("secound take result " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        /**
         * 该线程用来模拟朋友转账100
         */
        Thread addMoney = new Thread(() -> {

            int currentValue = accountValue.get();
            boolean result = accountValue.compareAndSet(currentValue, currentValue + 100);
            System.out.println("first add result " + result);

        });


        takeMoney1.start();
        takeMoney2.start();
        addMoney.start();

        takeMoney1.join();
        takeMoney2.join();
        addMoney.join();

        System.out.println("method compareAndSwapBug result is " + accountValue.get());
    }


}
