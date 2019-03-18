package com.wing.lynne.thread;

import java.util.concurrent.*;

/**
 *
 * 基于下面例子可以理解Java中线程中断这个概念
 *
 *
 * 尝试开一个只有一个线程的线程池
 * 执行的任务通过count累加，偶数次睡眠3s，基数次睡眠1s
 * 要求线程执行时间为2s，超过的时候，需要interrupt
 *
 *
 * 测试得出的最终结论
 * 线程的run方法内部的逻辑，运行多长时间，以及流程被应该被外部直接控制
 * 如果外部强行终结线程，可能会导致资源未释放等等不可预知的问题
 * 这也是原有的Thread类，stop方法被废弃的根本原因,详细原因可以查看stop方法的注释
 *
 * 正确的线程结束方法是通过在run方法内部设立interrupt检查，外部线程通过修改interrupt来实现线程正常停止
 * Future的cancel方法也是基于此实现的，但是如果run方法内部没有能够 响应中断 的方法，同时对线程中断状态进行判断，则当前run方法一定会执行完
 * 例子:
 *      可以将下面main方法中的38-40注释打开，会发现56行的返回值为true的情况下，但cpu会持续飙高
 *      39行的打印语句需要特殊注意，System.out.println会调用newline方法，该方法遇到线程中断异常之后，会中断当前线程
 *      所以如果将while(true)改为while(Thread.currentThread.isInterrupted())是可以停止的
 *
 */
public class InterruptThreadIfTimeOutDemo {

    public static int count = 0;

    public static void main(String[] args) throws InterruptedException, TimeoutException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 20; i++) {

            Future<?> future = executorService.submit(() -> {
                try {
                    count++;
                    if (count % 2 == 1) {
                        Thread.sleep(1000);
                    } else {
//                        while(true){
//                            System.out.println(Thread.currentThread().getName());
//                        }
                        Thread.sleep(3000);
                    }
                    System.out.println("count = " + count);
                } catch (InterruptedException  e) {
                    e.printStackTrace();
                    return null;
                }

                return count;
            });

            try {
                future.get(2, TimeUnit.SECONDS);
            } catch (Exception e) {
//                e.printStackTrace();
                boolean cancel = future.cancel(true);
                System.out.println("cancel result = " + cancel);
            }

        }


    }

}
