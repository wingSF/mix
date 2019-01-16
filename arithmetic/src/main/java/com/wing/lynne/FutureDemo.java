package com.wing.lynne;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            int cpuNumber = Runtime.getRuntime().availableProcessors();
            System.out.println("cpuNumber is " + cpuNumber);
            if (cpuNumber % 2 == 1) {
                return "wing";
            } else {
                throw new RuntimeException("callable exception");
            }
        });
        Thread.sleep(1000);
        try {
            String s = future.get();
            System.out.println("s = " + s);
        } catch (Exception e) {
            System.out.println("exception is " + e.getMessage());
        } finally {

        }

        Future future1 = executorService.submit((Callable) () -> {
            throw new Exception();
        });

        Thread.sleep(1000);
        try {
            Object o = future1.get();
            System.out.println("o = " + o);
        } catch (Exception e) {
            System.out.println("exception is " + e.getMessage());
        } finally {
            executorService.shutdown();
        }

    }
}
