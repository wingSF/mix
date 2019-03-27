package com.wing.lynne.queueDemo.nonBlockingQueue;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 非阻塞队列
 *
 * Q1.如何做到非阻塞
 * Q2.常见的使用方法，demo级别
 * Q3.本地性能测试，考虑一亿个数字任务的处理
 *
 * 特性总结
 * 1.unbound无界，决定于内存
 * 2.使用UNSAFE类，实现头尾的cas更新
 * 3.非阻塞类，当没有元素时，调用poll，返回null
 *
 */
public class ConcurrentLinkedQueueDemo {

    public static void main(String[] args) {

        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

        /**队列操作核心方法*/
        //添加一个元素
        concurrentLinkedQueue.offer(1);
        //移除head并返回
        concurrentLinkedQueue.poll();
        //只返回不移除
        concurrentLinkedQueue.peek();

        /**集合类的常见通用方法*/
        //add 底层调用的是offer方法
        concurrentLinkedQueue.add(1);
        concurrentLinkedQueue.addAll(Arrays.asList(1));
        //遍历队列 O(n)
        concurrentLinkedQueue.contains(1);
        //判空
        concurrentLinkedQueue.isEmpty();
        //返回内部类实现的迭代器
        concurrentLinkedQueue.iterator();



    }

}
