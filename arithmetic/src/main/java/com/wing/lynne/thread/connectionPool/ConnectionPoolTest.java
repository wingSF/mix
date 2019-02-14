package com.wing.lynne.thread.connectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 当并发的请求数小于等于连接池的初始化线程数量的时候，获取连接的操作不会超时，因为一直有连接空闲
 * 当并发的请求数一旦大于连接池的初始化线程数量的时候，就有可能出现，超时情况，这个时候，提高获取连接后的操作速度，能有效缓解，即尽快归还连接
 *
 * 线程池技术，以固定数量的线程处理任务
 *      减少了请求来临时线程创建的开销，以及任务结束线程的销毁
 *      避免了一任务一线程导致的线程数量太大，导致的频繁上下文切换
 *      面对大量任务的时候，能够平滑过度
 *
 * 劣势
 *      当大量任务出现的时候，后面的任务的响应时间不可控
 */

public class ConnectionPoolTest {

//    private static final int threadCount = 10;
    private static final int threadCount = 20;
//    private static final int threadCount = 30;
//    private static final int threadCount = 40;

    private static ConnectionPool connectionPool = new ConnectionPool(10);
    private static CountDownLatch startLock = new CountDownLatch(1);
    private static CountDownLatch endLock = new CountDownLatch(threadCount);

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger got = new AtomicInteger(0);
        AtomicInteger notGot = new AtomicInteger(0);
        int count = 20;

        for (int i = 0; i < threadCount; i++) {

            new Thread(new ConnectionRunner(count, got, notGot)).start();

        }

        startLock.countDown();

        endLock.await();

        System.out.println("线程数:" + threadCount);
        System.out.println("线程尝试获取连接次数:" + threadCount * count);
        System.out.println("获取连接成功数:" + got.get());
        System.out.println("获取连接失败数:" + notGot.get());

    }

    static class ConnectionRunner implements Runnable {

        private int count;
        private AtomicInteger got;
        private AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {

            try {
                startLock.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (count > 0) {

                try {
                    Connection connection = connectionPool.getConnection(100);

                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            connectionPool.releaseConnection(connection);
                            got.incrementAndGet();
                        }

                        /**
                         * public final int getAndIncrement() {
                         *         return unsafe.getAndAddInt(this, valueOffset, 1);
                         *     }
                         * public final int incrementAndGet() {
                         *         return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
                         *     }
                         *
                         */

                    } else {
                        notGot.incrementAndGet();
                    }

                } catch (InterruptedException e) {
                    notGot.getAndIncrement();
                } finally {
                    count--;
                }

            }

            endLock.countDown();
        }
    }

}
