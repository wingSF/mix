package com.wing.lynne.decorator.goodcase;

import java.math.BigDecimal;

public class CakeAddGrapeDecorator extends CakeDecorator{
    public CakeAddGrapeDecorator(Cake cake) {
        super(cake);
    }

    @Override
    public String getCakeMessage() {
        return super.getCakeMessage()+"加葡萄";
    }

    @Override
    public BigDecimal getPrice() {
        return super.getPrice().add(new BigDecimal("11.01"));
    }
}
