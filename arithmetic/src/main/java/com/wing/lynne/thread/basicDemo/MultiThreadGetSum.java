package com.wing.lynne.thread.basicDemo;

/**
 * 代码最终的输出是<=40000的，为什么这样呢
 *
 * 重点关注a++（JMM部分参考 Thread安全 文档）
 *
 *      T1先从主内存取出a，放入本地缓存T1—cache-a
 *      执行a++过程
 *          步骤1：先从T1-cache-a取值，放到stack
 *          步骤2：stack顶端数值+1
 *          步骤3：stack顶端数值覆盖T1-cache-a
 *      volatile可以保证T1执行步骤1每次都获取到主内存中最新的值
 *      如果T1执行步骤1和步骤2的中间，T2线程执行了步骤1，步骤2，步骤3，那T1再次执行步骤3就会发生数据覆盖了
 *
 */
public class MultiThreadGetSum {

    private static volatile int a = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                a++;
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                a++;
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                a++;
            }
        });
        Thread thread4 = new Thread(() -> {
            for (int j = 0; j < 10000; j++) {
                a++;
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println("a = " + a);

    }
}
