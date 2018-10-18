package com.wing.lynne.thread.basicDemo;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 该代码旨在表达多线程环境下，单个线程内部由于没有数据依赖关系导致的乱序执行
 * line 38 如果可以调整一个合适的值，让线程T1和T2合理竞争，就会出现
 * 1-0   0-1    1-1    0-0   四种情况
 * 心疼我的本本，烧的有点厉害，谁用谁知道
 */
public class MultiThreadReorderDemo {

    private static int a = 0;
    private static int b = 0;
    private static int x = 0;
    private static int y = 0;

    public static void main(String[] args) throws InterruptedException {

        Set<String> resultSet = new HashSet<>();

        int count = 0;

        for (; ; ) {

            if (count % 100 == 0) {
                Thread.sleep(100);
            }

            a = 0;
            b = 0;
            x = 0;
            y = 0;


            Thread thread1 = new Thread(() -> {
                try {
                    TimeUnit.NANOSECONDS.sleep(200000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1;
                x = b;
            });

            Thread thread2 = new Thread(() -> {
                b = 1;
                y = a;
            });

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();

            int currentSize = resultSet.size();
            resultSet.add(x + "-" + y);
            int resultSize = resultSet.size();

            if (currentSize != resultSize) {
                System.out.println("第" + count + "次:" + resultSet);
            }

            if (resultSet.size() == 4) {
                break;
            }

            count++;
        }

    }
}
