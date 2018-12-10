package com.wing.lynne.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LazySafeByConcurrentHashMap {

    private static final Map<String, Object> beanMap = new ConcurrentHashMap();

    public static Object getInstance(String name) {

        //todo 在spring的bean初始化过程中，使用了该数据结构，但是并没有加入同步逻辑，还需改进
        if (beanMap.get(name) == null) {
            System.out.println(Thread.currentThread().getName() + "-------");
            beanMap.put(name, new LazySafeByConcurrentHashMap());

//            此处可以使用putIfAbsent保证安全
//            putIfAbsent虽然是map接口中定义的方法之一，原始意义是当key对应的value不存在的时候，才放入元素
//            但是Map接口的默认实现是线程不安全的，高并发的情况下会有问题，concurrnet则不会有问题
            beanMap.putIfAbsent(name, new LazySafeByConcurrentHashMap());
        }


        return beanMap.get(name);
    }
}