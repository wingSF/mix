package com.wing.lynne.proxy.statics;

public class Proxy {

    private Target target;

    public Proxy(Target target) {
        this.target = target;
    }

    public void invoke(){
        System.out.println("proxy before business do something");
        target.doBusiness();
        System.out.println("proxy after business do something");
    }
}
