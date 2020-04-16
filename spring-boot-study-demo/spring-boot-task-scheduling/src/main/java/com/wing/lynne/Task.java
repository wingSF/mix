package com.wing.lynne;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class Task {

    //    @Scheduled(fixedRate = 1000)
    public void method1() {

        String date = new Date().toString();

        System.out.println(Thread.currentThread().getName() + "-" + date);

    }

    //    @Scheduled(fixedRate = 1000)
    public void method2() {

        String date = new Date().toString();

        System.out.println(Thread.currentThread().getName() + " : " + date);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Async
    @Scheduled(fixedRate = 1000)
    public void method3() {

        String date = new Date().toString();

        System.out.println(Thread.currentThread().getName() + " : " + date);

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Async("simpleSync")
    @Scheduled(fixedRate = 1000)
    public void method4() {

        String date = new Date().toString();

        System.out.println(Thread.currentThread().getName() +" : method4  : " + date);

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
