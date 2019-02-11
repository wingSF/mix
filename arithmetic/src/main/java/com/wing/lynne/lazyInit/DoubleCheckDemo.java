package com.wing.lynne.lazyInit;

/**
 * 双重检查锁的示例代码
 */
public class DoubleCheckDemo {

    private static Object instance;

    public static Object getInstance() {

        if (instance == null) {

            synchronized (DoubleCheckDemo.class) {

                if (instance == null)
                    instance = new Object();
                //对象的初始化过程分三步
                //1.分配空间
                //2.初始化
                //3.引用赋值
            }

        }

        return instance;
    }
}
