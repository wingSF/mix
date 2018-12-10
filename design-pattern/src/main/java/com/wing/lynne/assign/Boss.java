package com.wing.lynne.assign;

public class Boss {

    private Leader leader;

    public Boss(Leader leader) {
        this.leader = leader;
    }

    public void sendOrder(String orderCmd){
        leader.work(orderCmd);
    }
}

