package com.wing.lynne.singleton;

/**
 * 使用同步锁实现了并发访问的控制，
 * 但锁的范围太大，影响范围太大了
 */
public class LazySafeBySynchronizedMethod {

    private static LazySafeBySynchronizedMethod lazySafeBySynchronizedMethod;

    public static synchronized LazySafeBySynchronizedMethod getInstance() {
        if (lazySafeBySynchronizedMethod == null) {
            lazySafeBySynchronizedMethod = new LazySafeBySynchronizedMethod();
        }

        return lazySafeBySynchronizedMethod;
    }
}
