package com.wing.lynne.singleton;

/**
 * 使用类内部类的方式，实现了延迟加载
 * <p>
 * 既不会占用多余空间，也能做到优先于线程加载
 * <p>
 * 防止反射，增加一个标记位置
 */
public class LazySafeByInnerClass {

    private static boolean inited = false;

    private LazySafeByInnerClass() {
        if (inited == true) {
            throw new RuntimeException("不能反射该类");
        }
    }

    public static final LazySafeByInnerClass getInstance() {
        return LazyInnerClass.lazySafeByInnerClass;
    }

    private static class LazyInnerClass {

        private static final LazySafeByInnerClass lazySafeByInnerClass = new LazySafeByInnerClass();

    }
}
