package com.wing.lynne.queueDemo.blockingQueue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 在该队列的内部有俩个核心方法
 * siftUpComparable
 *      元素插入的时候使用该方法，将新增元素以二分的形式，插入到数组中的合适位置，保证该元素的父节点比自己小
 * siftDownComparable
 *      元素取出的时候使用该方法，返回数组中[0]位置的元素，将[length-1]位置的元素，移动到组数的左半部分
 *          或者在右半部分发现有元素，看不懂了。。。。。//todo 仍需耗时，求指点啊
 *
 * 自定义比较器会衍生出另外俩个方法
 * siftUpUsingComparator
 * siftDownUsingComparator
 *
 *
 */
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();

        priorityBlockingQueue.add(1);
        priorityBlockingQueue.add(4);
        priorityBlockingQueue.add(2);
        priorityBlockingQueue.add(5);

        Object take = priorityBlockingQueue.take();

        System.out.println(take);

    }
}
