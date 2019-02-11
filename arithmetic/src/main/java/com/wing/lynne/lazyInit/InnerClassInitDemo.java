package com.wing.lynne.lazyInit;

/**
 * 使用内部类进行初始化
 */
public class InnerClassInitDemo {


    public static class Instance {
        public static Object instance = new Object();
    }

    public Object getInstance() {
        return Instance.instance;
    }

}
