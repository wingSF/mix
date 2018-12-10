package com.wing.lynne.assign;

public class Worker {

    private String name;

    public Worker(String name) {
        this.name = name;
    }

    public void work(String orderCmd) {
        System.out.println(name + " doing " + orderCmd);
    }
}
