package com.wing.lynne.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedThreadPoolDemo {

    public static void main(String[] args) {

        oomExample();
    }

    /**
     * 执行该方法建议设置-Xms -Xmx可以尽快复现oom问题
     */
    private static void oomExample() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        while (true) {
            executorService.execute(() -> {
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(System.currentTimeMillis());
        }
    }

}
