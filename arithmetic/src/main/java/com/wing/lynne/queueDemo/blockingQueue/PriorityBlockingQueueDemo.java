package com.wing.lynne.queueDemo.blockingQueue;

import lombok.Builder;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 在该队列的内部有俩个核心方法
 * siftUpComparable
 * 元素插入的时候使用该方法，将新增元素以二分的形式，插入到数组中的合适位置，保证该元素的父节点比自己小
 * siftDownComparable
 * 元素取出的时候使用该方法，返回数组中[0]位置的元素，将[length-1]位置的元素，移动到组数的左半部分
 * 或者在右半部分发现有元素，看不懂了。。。。。//todo 仍需耗时，求指点啊
 * <p>
 * 自定义比较器会衍生出另外俩个方法
 * siftUpUsingComparator
 * siftDownUsingComparator
 */
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        PriorityBlockingQueue<User> priorityBlockingQueue = new PriorityBlockingQueue(6, (Comparator<User>) (o1, o2) -> o1.age - o2.age);

        User user1 = User.builder()
                .age(1)
                .name("sf")
                .build();

        User user2 = User.builder()
                .age(1)
                .name("sb")
                .build();

        User user3 = User.builder()
                .age(1)
                .name("sv")
                .build();

        User user4 = User.builder()
                .age(1)
                .name("wolock")
                .build();


        priorityBlockingQueue.add(user1);
        priorityBlockingQueue.add(user2);
        priorityBlockingQueue.add(user3);
        priorityBlockingQueue.add(user4);


        while(true){
            User user = priorityBlockingQueue.take();
            System.out.println(user.name);
            priorityBlockingQueue.add(user);
            TimeUnit.SECONDS.sleep(1);
        }

    }

    @Builder
    private static class User {
        private String name;
        private int age;
    }
}
