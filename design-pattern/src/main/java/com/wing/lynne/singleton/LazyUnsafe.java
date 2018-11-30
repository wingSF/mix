package com.wing.lynne.singleton;

/**
 * 线程不安全的懒加载模式
 */
public class LazyUnsafe {

    private static LazyUnsafe lazyUnsafe;

    public static LazyUnsafe getInstance() {

        //多线程调用在判断的时候会出现new多个对象的情况
        if (lazyUnsafe == null) {
            lazyUnsafe = new LazyUnsafe();
        }

        return lazyUnsafe;
    }
}
