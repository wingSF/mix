package com.wing.lynne.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CacheThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        TimeUnit.SECONDS.sleep(30);

        for (int i = 0; i < 100; i++)
            executorService.execute(() -> {
                try {

                    //通过这里或者jconsole会发现，线程瞬时会增加很多，经过下面sleep的时间+默认的线程idle时间（60s），线程会全部销毁
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(5);//T1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        TimeUnit.HOURS.sleep(5);
    }


}