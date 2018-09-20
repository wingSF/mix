package com.wing.lynne.thread.requestProcessChainDemo;

import java.util.concurrent.LinkedBlockingQueue;

public class PrintRequestProcessor extends Thread implements RequestProcessor {

  private LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue();

  private RequestProcessor nextRequestProcessor;

  public PrintRequestProcessor(RequestProcessor requestProcessor){
    this.nextRequestProcessor = requestProcessor;
  }


  @Override
  public void run() {

    while(true){
      try {
        Request request = linkedBlockingQueue.take();
        System.out.println("print object "+request.getName());
        nextRequestProcessor.processRequest(request);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

  @Override
  public void processRequest(Request request) {
    linkedBlockingQueue.add(request);
  }
}
