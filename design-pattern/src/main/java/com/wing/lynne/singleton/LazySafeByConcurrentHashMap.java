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
//            beanMap.putIfAbsent(name, new LazySafeByConcurrentHashMap());
        }


        return beanMap.get(name);
    }
}