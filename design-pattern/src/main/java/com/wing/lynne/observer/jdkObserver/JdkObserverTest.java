package com.wing.lynne.observer.jdkObserver;

import java.util.Observable;

public class JdkObserverTest {

    public static void main(String[] args) {

        //Observable
        MyObserver myObserver = new MyObserver();

        //Observer
        myObserver.addObserver((object,argsArray)->{
            System.out.println("收到变化");
            System.out.println(object);
            System.out.println(argsArray);
        });

        myObserver.setChanged();

        myObserver.notifyObservers();

        myObserver.setChanged();
        myObserver.notifyObservers("hello,world");



    }

    public static class MyObserver extends Observable {

        @Override
        public void setChanged(){
            super.setChanged();
        }
    }
}
