package com.wing.lynne;

import java.util.Arrays;
import java.util.LinkedList;

public class LinkedListDemo {

    public static void main(String[] args) {

        LinkedList linkedList = new LinkedList();

        linkedList.addFirst("a");
        System.out.println(linkedList);

        boolean result = linkedList.add("b");
        System.out.println(linkedList);

        //插入时如果带索引，下层会做一次折半查找 node()了解下
        linkedList.add(1, "c");
        System.out.println(linkedList);

        linkedList.addLast("d");
        System.out.println(linkedList);

        linkedList.addAll(Arrays.asList("e", "f"));
        System.out.println(linkedList);

        linkedList.addAll(1, Arrays.asList("g", "h"));
        System.out.println(linkedList);

        linkedList.offer("i");
        System.out.println(linkedList);

        linkedList.offerFirst("j");
        System.out.println(linkedList);

        linkedList.offerLast("k");
        System.out.println(linkedList);

        //添加到栈顶
        linkedList.push("l");
        System.out.println(linkedList);

        linkedList.set(0, "替换");
        System.out.println(linkedList);

        //查看但不弹出类方法
        linkedList.element();

        linkedList.get(0);
        linkedList.getFirst();
        linkedList.getLast();
        System.out.println(linkedList);

        linkedList.peek();
        linkedList.peekFirst();
        linkedList.peekLast();
        System.out.println(linkedList);

        linkedList.peek();
        linkedList.peekFirst();
        linkedList.peekLast();
        System.out.println(linkedList);

        //弹出
        linkedList.pop();
        System.out.println(linkedList);

        linkedList.remove();
        System.out.println(linkedList);

        //如果被删除元素为null，则删除第一个null元素，没有返回false
        linkedList.remove("b");
        System.out.println(linkedList);

        linkedList.remove(0);
        System.out.println(linkedList);

        linkedList.removeFirst();
        System.out.println(linkedList);

        //就是调用了remove方法
        linkedList.removeFirstOccurrence("c");
        System.out.println(linkedList);

        linkedList.removeLast();
        System.out.println(linkedList);

        //倒序遍历，删除
        linkedList.removeLastOccurrence("e");
        System.out.println(linkedList);

    }
}
