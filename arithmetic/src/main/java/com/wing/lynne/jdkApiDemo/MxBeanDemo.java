package com.wing.lynne.jdkApiDemo;

import org.joda.time.DateTime;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;

public class MxBeanDemo {

    public static void main(String[] args) {

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        String bootClassPath = runtimeMXBean.getBootClassPath();
        System.out.println(bootClassPath);

        String classPath = runtimeMXBean.getClassPath();
        System.out.println(classPath);

        String name = runtimeMXBean.getName();
        System.out.println(name.substring(0, name.indexOf("@")));

        long startTime = runtimeMXBean.getStartTime();
        System.out.println(new DateTime(startTime).toString("YYYY-MM-dd HH:mm:ss"));

        long uptime = runtimeMXBean.getUptime();
        System.out.println(uptime);


        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        int threadCount = threadMXBean.getThreadCount();
        System.out.println(threadCount);


        List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        String name1 = operatingSystemMXBean.getName();
        System.out.println(name1);
    }
}
