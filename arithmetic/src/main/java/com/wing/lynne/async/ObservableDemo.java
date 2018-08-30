package com.wing.lynne.async;

import java.util.Observable;

public class ObservableDemo {

    /*
        Observable
        Observer

        Observer.update(Observable,Object)
     */


    public static void main(String[] args) {

        MyObservable observable = new MyObservable();

        observable.addObserver((ob, value) -> System.out.println(ob + "_1_" + value));
        observable.addObserver((ob, value) -> System.out.println(ob + "_2_" + value));
        observable.addObserver((ob, value) -> System.out.println(ob + "_3_" + value));

        observable.setChanged();
        observable.notifyObservers(100);

    }

    public static class MyObservable extends Observable{

        public void setChanged(){
            super.setChanged();
        }
    }
}
