package com.wing.lynne.proxy.dynamic.jdk;

public class Target implements TargetInterface{

    @Override
    public void doBusiness(){
        System.out.println("do my business");
    }
}
