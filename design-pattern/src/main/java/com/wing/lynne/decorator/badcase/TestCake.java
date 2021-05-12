package com.wing.lynne.decorator.badcase;

public class TestCake {

    public static void main(String[] args) {

        Cake cake = new Cake();
        System.out.println(cake.getCakeMessage() + "--" + cake.getPrice());

        CakeAddMango cakeAddMango = new CakeAddMango();
        System.out.println(cakeAddMango.getCakeMessage() + "--" + cakeAddMango.getPrice());

        CakeAddMangoAddGrape cakeAddMangoAddGrape = new CakeAddMangoAddGrape();
        System.out.println(cakeAddMangoAddGrape.getCakeMessage()+"--"+cakeAddMangoAddGrape.getPrice());
    }
}
