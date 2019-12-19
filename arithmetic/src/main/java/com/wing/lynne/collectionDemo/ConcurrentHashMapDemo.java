package com.wing.lynne.collectionDemo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * jdk1.8的时候会出现死循环问题，具体过程待分析
 *  据说该问题由于cas操作的ABA引发
 *      解决该问题可以用使用AtomicStampedReference
 * jdk1.9该问题已经修复
 */
public class ConcurrentHashMapDemo {

    public static void main(String[] args) {

        System.out.println("AaAa".hashCode());
        System.out.println("BBBB".hashCode());

//        provided by buildup chao
//        Map<Integer, Integer> map1 = new ConcurrentHashMap<>(16);
//        map1.computeIfAbsent(12, (k) -> {
//            map1.put(k, k); // will generate dead loop and result in high CPU.
//            return k;
//        });

        Map<String, Integer> map2 = new ConcurrentHashMap<>(16);
        map2.computeIfAbsent("AaAa", key -> {
            return map2.computeIfAbsent("BBBB", key2 -> 42);
        });

    }


}
