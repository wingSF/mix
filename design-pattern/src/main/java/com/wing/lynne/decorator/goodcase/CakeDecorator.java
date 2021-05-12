package com.wing.lynne.decorator.goodcase;

import java.math.BigDecimal;

public abstract class CakeDecorator extends Cake{

    private Cake cake;

    public CakeDecorator(Cake cake) {
        this.cake = cake;
    }

    @Override
    public String getCakeMessage() {
        return cake.getCakeMessage();
    }

    @Override
    public BigDecimal getPrice() {
        return cake.getPrice();
    }
}
