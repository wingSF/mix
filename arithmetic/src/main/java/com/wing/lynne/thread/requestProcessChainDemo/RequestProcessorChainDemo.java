package com.wing.lynne.thread.requestProcessChainDemo;

/**
 * 方法入口
 */
public class RequestProcessorChainDemo {

  public static void main(String[] args) {

    SaveRequestProcessor saveRequestProcessor = new SaveRequestProcessor(null);

    PrintRequestProcessor printRequestProcessor = new PrintRequestProcessor(saveRequestProcessor);

    saveRequestProcessor.start();

    printRequestProcessor.start();

    Request request = new Request();
    request.setName("wing");

    printRequestProcessor.processRequest(request);

  }

}
