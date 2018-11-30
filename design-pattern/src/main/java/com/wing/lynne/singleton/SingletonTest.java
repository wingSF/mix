package com.wing.lynne.singleton;

import java.util.concurrent.CountDownLatch;

public class SingletonTest {

    public static void main(String[] args) {

        int count = 200;

        final CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {

            new Thread(new Runnable() {
                public void run() {

                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    hungry式
//                    Hungry instance = Hungry.getInstance();
//                    System.out.println(instance);

//                    inner类方式
//                    LazySafeByInnerClass lazySafeByInnerClass = LazySafeByInnerClass.getInstance();
//                    System.out.println(lazySafeByInnerClass);

//                    不安全的，偶现不同对象
//                    LazyUnsafe unsafe = LazyUnsafe.getInstance();
//                    System.out.println(unsafe);

//                    同步方法
//                    LazySafeBySynchronizedMethod lazySafeBySynchronizedMethod = LazySafeBySynchronizedMethod.getInstance();
//                    System.out.println(lazySafeBySynchronizedMethod);

//                    双重检查
//                    LazySafeBySynchronizedCode lazySafeBySynchronizedCode = LazySafeBySynchronizedCode.getInstance();
//                    System.out.println(lazySafeBySynchronizedCode);


                    Object object = LazySafeByConcurrentHashMap.getInstance("com.wing.lynne.singleton.LazySafeByConcurrentHashMap");
                    System.out.println(object);

                }
            }).start();

            countDownLatch.countDown();

        }


    }

}
