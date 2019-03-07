package com.wing.lynne.collectionDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GenericTypeDemo {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 演示向一个被范型修饰的集合中插入一个与当前类型不一致的元素
         * 即声明Integer，插入一个String
         *
         */
        List<Integer> list = addDiffTypeToGenericCollection();

        /**
         * 为了防止上述情况的产生，使用Collections.checked*
         * 会在数据添加或者修改的过程中增加typecheck逻辑
         *
         * 但是在集合初始化的时候，并没有做检查
         *
         */
        addDiffTypeToCheckedCollection(list);

    }

    private static List<Integer> addDiffTypeToGenericCollection() {
        List<Integer> list = new ArrayList(Arrays.asList(1, 2, 3));

        List referenceList = list;

        referenceList.add("wing");

        for (Object obj : list) {
            System.out.println(obj);
        }

        return list;
    }

    private static void addDiffTypeToCheckedCollection(List<Integer> param) throws InterruptedException {

        List<Integer> list = Collections.checkedList(param, Integer.class);

        for (Object obj : list) {
            System.out.println(obj);
        }

        System.out.println("--------------------");

        List referenceList = list;

        //为了console输出整齐
        TimeUnit.SECONDS.sleep(1);

        referenceList.add("lynne");

    }

}
