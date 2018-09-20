package com.wing.lynne.thread.basicDemo;

public class ThreadGroupDemo {

  static ThreadGroup threadGrou = null;

  public static void main(String[] args) throws InterruptedException {

    Thread thread = new MyThread();

    thread.start();

    ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
    System.out.println("main方法" + threadGroup.getName());

//    thread.join();
    while(true){
      System.out.println(threadGroup == threadGrou);
    }
  }

  public static class MyThread extends Thread {

    @Override
    public void run() {

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      threadGrou = Thread.currentThread().getThreadGroup();

      while(true){

      }

    }
  }

}
