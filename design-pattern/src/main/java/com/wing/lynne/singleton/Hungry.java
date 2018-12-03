package com.wing.lynne.singleton;

/**
 * 类加载时，创建静态的单例对象
 * 对象的创建，先与线程的调用，线程安全
 *
 * 缺点：长时间的占用内存
 */
public class Hungry {

    private static final Hungry instance = new Hungry();

    private Hungry(){
    }

    public static Hungry getInstance() {
        return instance;
    }

}
