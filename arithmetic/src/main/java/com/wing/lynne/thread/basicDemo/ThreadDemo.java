package com.wing.lynne.thread.basicDemo;

public class ThreadDemo {

  public static void main(String[] args) throws InterruptedException {

    /*
      缺点
        1.线程类结构关系需要继承，不太合适
        2.旧的变成模型中，只有synchronized控制线程同步
          关键字局限于方法声明和代码块，有一定的局限性
        3.线程什么时间执行结束，这个是未知的，如果我想知道线程执行的结果，必须要使用join方法等到线程执行结束，
          然后再get受影响的数据才能得到最正确的答案
        4.无法管理线程，及没法外部控制线程结束或者暂停
        5.线程执行完的返回值没有处理
     */

    MyThread thread = new MyThread("myThread");

    System.out.println(thread.getThreadGroup().getName());

    System.out.println("1" + thread.getState().name());

    //此处仅仅是为了启动同名线程
    MyThread thread1 = new MyThread("myThread");
    thread1.start();

    thread.start();

    System.out.println("3" + thread.getState().name());
    thread.join();

    System.out.println("4" + thread.getState().name());

  }

  public static class MyThread extends Thread {

    private ThreadGroup threadGroup;

    public MyThread(String name) {
      super(name);
    }

    public MyThread(ThreadGroup threadGroup) {
      this.threadGroup = threadGroup;
    }

    @Override
    public void run() {
      System.out.println(Thread.currentThread().getName());
      System.out.println("2" + Thread.currentThread().getState().name());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      //线程父子关系
      Thread thread = new Thread(() -> {
        System.out.println("son thread " + currentThread().getThreadGroup().getName());
      }, "son");
      thread.start();

    }

  }
}
