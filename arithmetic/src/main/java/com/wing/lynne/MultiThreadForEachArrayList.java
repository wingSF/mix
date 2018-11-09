package com.wing.lynne;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 多线程消费arrayList中的数据
 */
public class MultiThreadForEachArrayList {

    static Integer index = 0;
    private static Object object = new Object();

    public static void main(String[] args) throws IOException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(10);

        //开10个核心线程池，最大30
        ThreadPoolExecutor threadPoolExecutor
                = new ThreadPoolExecutor(10, 30, 1000, TimeUnit.MILLISECONDS, workQueue);

//       收到的消费会有异常的第一个版本
//        for (int i = 0; i < 10; i++) {
//            threadPoolExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
////     问题原因： index是普通类型变量，此处并发执行的时候，index会保留旧值
//                    while (index < list.size()) {
//                        try {
//                            synchronized (index) {
//                                if (index < list.size()) {
//                                    System.out.println("[" + Thread.currentThread().getName() + "]-" + list.get(index));
//                                    index++;
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//            });
//        }


//        第一次改进的版本，还是有问题
//        for (int i = 0; i < 10; i++) {
//            threadPoolExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
////      此处锁对象是index，所以线程获取这个对象的时候等待，一旦有线程释放，就能获取资源，一旦拿到就继续执行，所以会重复
//                    synchronized (index) {
//                        while (index < list.size()) {
//                            try {
//                                if (index < list.size()) {
//                                    System.out.println("[" + Thread.currentThread().getName() + "]-" + list.get(index));
//                                    index++;
//                                }
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            });
//        }


        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    //此处将锁对象换成全局的一个对象，只有拿到资源，才能访问后续变量，这样就能保证没有问题
                    synchronized (object) {
                        while (index < list.size()) {
                            try {
                                if (index < list.size()) {
                                    System.out.println("[" + Thread.currentThread().getName() + "]-" + list.get(index));
                                    index++;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }

        threadPoolExecutor.shutdown();
    }
}
