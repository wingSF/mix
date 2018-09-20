package com.wing.lynne.thread.basicDemo;

public class VisableDemo2 {

    /**
     * 前一个demo反映出了可见行问题，及线程对数据修改，别的线程不可见
     * 这个demo反映出了volatile并不能完全解决线程安全问题
     * 保证线程安全需要同时解决   可见性/有序性/原子性
     * 使用synchronized方法可以解决上述3个问题
     */
    public static volatile int i = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {

//            for (int j = 0; j < 10000; j++) {
//                i++;
//            }
            syncMethod();

        });

        Thread thread2 = new Thread(() -> {

//            for (int j = 0; j < 10000; j++) {
//                i++;
//            }
            syncMethod();

        });


        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("i = " + i);
    }

    public synchronized static void syncMethod() {
        for (int j = 0; j < 10000; j++) {
            i++;
        }
    }
}
