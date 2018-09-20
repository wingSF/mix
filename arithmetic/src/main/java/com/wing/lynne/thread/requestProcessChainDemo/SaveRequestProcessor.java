package com.wing.lynne.thread.requestProcessChainDemo;

import java.util.concurrent.LinkedBlockingQueue;

public class SaveRequestProcessor extends Thread implements RequestProcessor {

  private LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue();

  private RequestProcessor nextRequestProcessor;

  public SaveRequestProcessor(
      RequestProcessor nextRequestProcessor) {
    this.nextRequestProcessor = nextRequestProcessor;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Request request = linkedBlockingQueue.take();
        System.out.println("save request " + request.getName());
        if (nextRequestProcessor != null) {
          nextRequestProcessor.processRequest(request);
        }
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
