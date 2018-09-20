package com.wing.lynne.publishAndSubscribe;

import java.util.concurrent.CompletableFuture;
//需要java9
//import java.util.concurrent.SubmissionPublisher;

public class CompletFutureDemo {


  public static void main(String[] args) throws InterruptedException {

//    method1();
    while(true) {
      method2();
    }
//    return;

  }

  private static String method2() {

    CompletableFuture.supplyAsync(() -> {
      System.out.println(Thread.currentThread().getName());
      return "hello";
    }).thenApplyAsync(result -> result + "world")
        .thenAccept(value -> System.out.println(Thread.currentThread().getName() + "-" + value))
        .whenComplete((v, error) -> System.out.println(Thread.currentThread().getName() + "-" + "执行结束"))
        .join();

    return "";
  }


  /*
    此处Thread  join相对于try的位置，会影响CompletableFuture下的方法执行输出
    //todo 不得解…
   */
//  private static void method1() throws InterruptedException {
//    try (SubmissionPublisher publisher = new SubmissionPublisher()) {
//
//      CompletableFuture completableFuture = publisher.consume(value -> {
//        System.out.println("1");
//      }).thenRunAsync(() -> {
//        System.out.println("2");
//
//      }).thenRun(() ->
//          System.out.println("3"));
//
//      // 提交数据到各个订阅器
//      publisher.submit(100);
//
////      System.out.println(Thread.currentThread().getName());
////      Thread.currentThread().join(1000L);
//    }
//
//    System.out.println(Thread.currentThread().getName());
//    Thread.currentThread().join(1000L);
//  }


}



