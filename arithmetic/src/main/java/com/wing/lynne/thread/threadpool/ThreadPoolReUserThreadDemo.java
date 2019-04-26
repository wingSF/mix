package com.wing.lynne.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolReUserThreadDemo {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 1, TimeUnit.SECONDS, new ArrayBlockingQueue(1));

//        当注释开启前，会发现打印出的线程名称一直没有变化，但是当注释打开后，sleep前后执行的线程名称发生了变化，证明线程没有被复用，是新创建的
//        threadPoolExecutor.allowCoreThreadTimeOut(true);

        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));

        TimeUnit.SECONDS.sleep(10);

        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));

        threadPoolExecutor.shutdown();
    }
}
