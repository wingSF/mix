package com.wing.lynne.singleton;

/**
 * 虽然用了Synchronized，但是由于是代码段的控制，不是每次请求都会走到同步锁里面
 * 只要对象被实例化一次，就不会在进入同步代码段
 *
 * todo DCL  double check 造成的不完整对象
 */
public class LazySafeBySynchronizedCode {

    private static LazySafeBySynchronizedCode lazySafeBySynchronizedCode;

    public static LazySafeBySynchronizedCode getInstance() {

        if (lazySafeBySynchronizedCode == null) {

            synchronized (LazySafeBySynchronizedCode.class) {

                //同时多个线程等待锁对象，第一个操作完之后，后续的线程会再次进入，需要执行再次判断
                if (lazySafeBySynchronizedCode == null) {

                    lazySafeBySynchronizedCode = new LazySafeBySynchronizedCode();
                }

            }

        }

        return lazySafeBySynchronizedCode;

    }

}
