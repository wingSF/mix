package com.wing.lynne.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SubmissionPublisher;

public class CompletFutureDemo {

  /*
    此处Thread  join相对于try的位置，会影响CompletableFuture下的方法执行输出
    //todo 不得解
   */
  public static void main(String[] args) throws InterruptedException {

    try (SubmissionPublisher publisher = new SubmissionPublisher()) {

      CompletableFuture completableFuture = publisher.consume(value -> {
        System.out.println("1");
      }).thenRunAsync(() -> {
        System.out.println("2");

      }).thenRun(() ->
          System.out.println("3"));

      // 提交数据到各个订阅器
      publisher.submit(100);

//      System.out.println(Thread.currentThread().getName());
//      Thread.currentThread().join(1000L);
    }


    System.out.println(Thread.currentThread().getName());
    Thread.currentThread().join(1000L);

  }


}



