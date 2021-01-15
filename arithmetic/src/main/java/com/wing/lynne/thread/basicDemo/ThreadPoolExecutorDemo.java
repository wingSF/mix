package com.wing.lynne.thread.basicDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {

        ThreadLocal threadLocal = new ThreadLocal();

        Runnable runnable = () -> {
            Object o = threadLocal.get();
            System.out.println(o);
            threadLocal.set("heihei");
        };

        Runnable runnable1 = () -> {
            ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
            String s = stringThreadLocal.get();
            System.out.println("before set :"+s);
            stringThreadLocal.set("haha");
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(runnable);
        executorService.execute(runnable);

        executorService.execute(runnable1);
        executorService.execute(runnable1);

    }
}
