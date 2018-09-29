package com.wing.lynne.thread.basicDemo;

import java.util.concurrent.TimeUnit;

/**
 * 该代码用来演示，main线程修改共享变量flag，别的线程能否读取到新的flag值
 * flag分为
 *      普通常量
 *      被volatile修饰常量
 *
 * 前者程序无法停止，后者程序可以停止
 *
 *
 * 关于程序停止：
 *      程序停止是表象，实质是JVM退出
 *      JVM退出条件，所有的前台线程都执行结束，或者调用System.exit()
 *      所以main线程不是在等待thread线程，而且jvm没有退出！！！
 */
public class StopThreadByVolatileFlag {

//    是否用volatile修饰共享变量
//    private static boolean flag = true;
    private static volatile  boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            while (flag) {
//                System.out.println(Thread.currentThread().getName() + " running");

//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                /**
                 * 上面俩段代码，打开注释会影响到可见行问题的问题，目前无解，如有知道原理之人，还忘告知下
                 *
                 * cpu时间片的切换会导致缓存失效   mic说的
                 * 但是如果上述成立，注释掉上述俩段代码，运行后应该在未来某个时刻结束，但是并没有
                 *
                 * 根本解决需要javap -v分析字节码，然而水平还达不到啊
                 */
            }
        });

        thread.start();

        TimeUnit.SECONDS.sleep(1);

        flag = false;

    }
}
