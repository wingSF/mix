package com.wing.lynne;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 模拟实现通过缓冲行填充提升效率
 */
public class CacheLinePadding {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int processorNumber = Runtime.getRuntime().availableProcessors();

        Counter[] counterArray = new Counter[processorNumber];

        for (int i = 0; i < processorNumber; i++) {
            counterArray[i] = new Counter();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(processorNumber);

        List<Future<String>> resultList = new ArrayList();

        for (int i = 0; i < processorNumber; i++) {
            Future<String> submit = executorService.submit(new MyCallable(counterArray, i));
            resultList.add(submit);
        }


        int count = 0;

        while (true) {

            if (resultList.get(count).isDone() == true) {
                count++;
            }

            if (count == processorNumber - 1) {
                break;
            }

        }

        for (Future future : resultList) {
            System.out.println(future.get());
        }

        executorService.shutdown();

    }

    static class Counter {
        volatile long v;
//        long v1,v2,v3,v4,v5;
//        long v;
    }

    static class MyCallable implements Callable<String> {

        private Counter[] counterArray;
        private int index;

        public MyCallable(Counter[] counterArray, int index) {
            this.counterArray = counterArray;
            this.index = index;
        }

        @Override
        public String call() {
            long start = System.nanoTime();

            for (int i = 0; i < 1000000000; i++) {
                counterArray[index].v++;
            }

            long end = System.nanoTime();

            return Thread.currentThread().getName() + "：耗时 " + (end - start) + " 纳秒";
        }
    }

}


/**
 * 性能对比，
 * 没有打开注释
 * pool-1-thread-1：耗时 52488979441 纳秒
 * pool-1-thread-2：耗时 52488943266 纳秒
 * pool-1-thread-3：耗时 52488297271 纳秒
 * pool-1-thread-4：耗时 52484781151 纳秒
 * 打开注释(line 60)
 * pool-1-thread-1：耗时 13108632969 纳秒
 * pool-1-thread-2：耗时 13108589842 纳秒
 * pool-1-thread-3：耗时 13105287557 纳秒
 * pool-1-thread-4：耗时 13103865653 纳秒
 * 去掉volatile修饰
 * pool-1-thread-1：耗时 4387733857 纳秒
 * pool-1-thread-2：耗时 4387076932 纳秒
 * pool-1-thread-3：耗时 4386774589 纳秒
 * pool-1-thread-4：耗时 3103826412 纳秒
 * <p>
 * 总结分析
 * <p>
 * 1.在java中被vovaltile修饰变量，要求被修改后立即刷新到主内存
 * 2.cpu操作内存中的数据的时候，并不会直接操作主内存，而是先将主内存中的数据load到缓存中(l1,l2,l3)操作，操作完成之后一次性将缓存中的修改刷新到主内存
 * 3.被volatile修饰的变量在更新时方式与第二点中略有不同
 * 3.1.cpu先从主内存中load该变量在主内存中的值到高速缓存中
 * 3.2.然后对该缓存中的该变量的副本进行操作
 * 3.3.操作完成之后，由于是volatile修饰的变量，会立即刷新该变量所在的 '缓冲行' 到主内存中，保证该变量对多个线程的可见性
 * <p>
 * 缓冲行:
 * 缓冲行是缓冲中最小的存储单位，可以网络上查找命令，查看自己的cpu的缓冲行的大小。
 * ex: mac的 sysctl machdep.cpu ，machdep.cpu.cache.linesize = 64
 * java对象占用空间大小计算
 * java对象头             32位
 * java对象指针            32位
 * java对象成员变量          在该例子中，如果不打开注释就是8位，如果打开注释就是 n*8
 * java对象占用空间大小和缓存行大小有什么关系呢？
 * 假设如果是俩个没有打开注释的Counter对象 a和b，如果a和b 被 俩个线程 先后从 主内存 加载到 缓存中 ，必然存在一个缓冲行中有一部分数据是a的，另外一部分是b的，在a操作完之后，要立马刷新缓冲行到主内存，这个时候b的数据就被丢弃了，当更新b的时候需要重新从主内存中加载，这个模型叫伪共享
 * 如果是俩个打开注释的Counter对象 a和b，由于有其它成员变量的存在，只要长度恰好合适，那么一定能保证存储a信息的缓冲行没有存储b的信息，这个时候，就不会存在刷新a导致重新加载b，自然效率就高了
 * <p>
 * 4.disruptor框架的RingBuffer的效率比jdk的ArrayBlockingQueue效率高的原因就在这里
 * 5.不适合场景
 *      缓冲行不是64位的处理器
 *      不被频繁写的数据
 *          如果不是被频繁写的话，加填充就打不到想要的效果，反而浪费宝贵的空间
 * 6.如果增加填充的姿势不合理的话，可能会被jvm优化掉
 *
 */