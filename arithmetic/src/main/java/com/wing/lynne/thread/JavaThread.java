package com.wing.lynne.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 使用JMX观察最小的main方法会启动哪些线程
 */
public class JavaThread {

    public static void main(String[] args) {

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId()+":"+threadInfo.getThreadName());
        }

    }
}
