package com.wing.lynne.thread.basicDemo;

import java.util.concurrent.TimeUnit;

/**
 * 该代码用来演示，main线程修改共享变量flag，别的线程能否读取到新的flag值
 * flag分为
 * 普通常量
 * 被volatile修饰常量
 * <p>
 * 前者程序无法停止，后者程序可以停止
 * <p>
 * <p>
 * 关于程序停止：
 * 程序停止是表象，实质是JVM退出
 * JVM退出条件，所有的前台线程都执行结束，或者调用System.exit()
 * 所以main线程不是在等待thread线程，而且jvm没有退出！！！
 */
public class StopThreadByVolatileFlag {

    //    是否用volatile修饰共享变量
    private static boolean flag = true;

    private static Object object  = new Object();
//    private static volatile  boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            while (flag) {

                //过一个类锁可以暂停线程，对象锁不行  继续留着疑问
                synchronized (object) {

                }
//                System.out.println(Thread.currentThread().getName() + " running");

//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                /**
                 * 上面俩段代码，打开注释会影响到可见性问题，目前无解，如有知道原理之人，还忘告知下
                 * 哈哈，自己找到正确的答案
                 *
                 *
                 *
                 * cpu时间片的切换会导致缓存失效   mic说的     时间片切换，再次切换回来的时候相当于，数据恢复，是不能该改变数据的，或者就算要改，也得符合一定的规则，不能随便改
                 * 但是如果上述成立，注释掉上述俩段代码，运行后应该在未来某个时刻结束，但是并没有
                 *
                 * 根本解决需要javap -v分析字节码，然而水平还达不到啊
                 *
                 *
                 *
                 * 最终答案
                 *    System.out.println中有synchronized，在锁释放的时候，会刷新本地内存中的共享变量到主内存
                 *    如果使用 new Object()作为锁对象的话，由于是当前线程独享的，根本不需要加锁，所以在编译期，被优化掉了
                 *    使用static 的new Object()或者Class文件作为锁对象，这个时候，锁对象是所有线程可以访问的，不会被优化掉，在锁释放的时候，一定会刷新本地内存，线程就会停下来了
                 */
            }
        });

        thread.start();

        TimeUnit.SECONDS.sleep(1);

        flag = false;

    }
}
