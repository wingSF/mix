package com.wing.lynne.decorator.goodcase;

public class TestCake {
    public static void main(String[] args) {

        Cake cake = null;

        cake = new BaseCake();
        System.out.println(cake.getCakeMessage()+cake.getPrice());

        cake = new CakeAddMangoDecorator(cake);
        System.out.println(cake.getCakeMessage()+cake.getPrice());

        cake = new CakeAddGrapeDecorator(cake);
        System.out.println(cake.getCakeMessage()+cake.getPrice());

        cake = new CakeAddMangoDecorator(cake);
        System.out.println(cake.getCakeMessage()+cake.getPrice());

    }
}
