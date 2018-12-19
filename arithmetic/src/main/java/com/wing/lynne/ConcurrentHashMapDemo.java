package com.wing.lynne;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * jdk1.8的时候会出现死循环问题，具体过程待分析
 * jdk1.9该问题已经修复
 */
public class ConcurrentHashMapDemo {

    public static void main(String[] args) {

        System.out.println("AaAa".hashCode());
        System.out.println("BBBB".hashCode());


        Map<String, Integer> map = new ConcurrentHashMap<>(16);
        map.computeIfAbsent("AaAa", key -> {
            return map.computeIfAbsent("BBBB", key2 -> 42);
        });

    }


}
