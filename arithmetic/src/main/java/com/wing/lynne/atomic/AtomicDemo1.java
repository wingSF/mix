package com.wing.lynne.atomic;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo1 {

    /**
     * 测试compareAndSet的调用过程和返回值判断
     */
    @Test
    public void test1() {

        AtomicInteger atomicInteger = new AtomicInteger(3);

        boolean result = atomicInteger.compareAndSet(4, 5);

        String stringResult = "true".equals(result) ? "成功" : "失败";

        System.out.println("更新操作执行:" + stringResult + atomicInteger.get());

    }

    /**
     * 通过while true+cas操作，实现一个等待资源，然后修改的操作
     * 注意:此处一定要注意修改的条件，如果始终不满足的话，程序会一直空轮询，就算会挂起，唤醒后还是没办法继续执行
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {

        AtomicInteger atomicInteger = new AtomicInteger(3);

        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {

            long start = System.nanoTime();

            int count = 0;
            while (true) {

                boolean result = atomicInteger.compareAndSet(4, 5);

                count++;
                if (result) {
                    countDownLatch.countDown();
                    long end = System.nanoTime();
                    System.out.println("更新成功，重试次数:" + count + ";耗时:" + (end - start) + "纳秒");

                    break;
                }
            }
        }).start();

        new Thread(() -> {
            atomicInteger.set(4);
            countDownLatch.countDown();
        }).start();

        countDownLatch.await();

        System.out.println(atomicInteger.get());

    }

}
