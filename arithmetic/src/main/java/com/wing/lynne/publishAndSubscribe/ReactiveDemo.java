package com.wing.lynne.publishAndSubscribe;

//需要java9
//import java.util.concurrent.Flow;
//import java.util.concurrent.Flow.Subscription;
//import java.util.concurrent.SubmissionPublisher;

public class ReactiveDemo {

  public static void main(String[] args) throws InterruptedException {

//    try (SubmissionPublisher submissionPublisher = new SubmissionPublisher()) {
//
//      submissionPublisher.subscribe(new MySubscriber());
//      submissionPublisher.subscribe(new MySubscriber());
//      submissionPublisher.subscribe(new MySubscriber());
//
//      int submit = submissionPublisher.submit(100);
//
//    }
//    Thread.currentThread().join(1000);

  }


//  public static class MySubscriber implements Flow.Subscriber {
//
//    @Override
//    public void onSubscribe(Subscription subscription) {
//      subscription.request(1);
//      System.out.println(Thread.currentThread().getName() + "-" + subscription);
//    }
//
//    @Override
//    public void onNext(Object item) {
//      System.out.println(Thread.currentThread().getName() + "-" + item);
//    }
//
//    @Override
//    public void onError(Throwable throwable) {
//      System.out.println(Thread.currentThread().getName() + "-" + "throw exception");
//    }
//
//    @Override
//    public void onComplete() {
//      System.out.println(Thread.currentThread().getName() + "-" + "finish");
//    }
//  }

}
