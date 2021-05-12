package com.wing.lynne.decorator.goodcase;

import java.math.BigDecimal;

public class CakeAddMangoDecorator extends CakeDecorator{

    public CakeAddMangoDecorator(Cake cake) {
        super(cake);
    }

    @Override
    public String getCakeMessage() {
        return super.getCakeMessage()+"加芒果";
    }

    @Override
    public BigDecimal getPrice() {
        return super.getPrice().add(new BigDecimal("10.59"));
    }
}
