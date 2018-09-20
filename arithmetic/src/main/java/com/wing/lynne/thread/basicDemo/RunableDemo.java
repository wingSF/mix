package com.wing.lynne.thread.basicDemo;

import java.util.concurrent.atomic.AtomicInteger;

public class RunableDemo {

  /*
    升级为Runnable之后，线程类摆脱类extends Thread的方式
   */
  /*
    所有的前台线程都执行结束/调用System.exit   jvm才会退出
   */
  public static void main(String[] args) throws InterruptedException {

    AtomicInteger i = new AtomicInteger();

    Thread thread = new Thread(() -> {

      while (i.intValue() < 100000000) {
        i.getAndIncrement();
      }

    }, "myThread");

    thread.start();

    System.out.println("first get i = " + i);

    Thread.sleep(1000);

    System.out.println("sencond get i = " + i);

    /*
      使用不使用join方法将直接决定最后final获取的值，是不是线程执行的最终结果
     */
    thread.join();

    System.out.println("final get i = " + i);

  }

}
