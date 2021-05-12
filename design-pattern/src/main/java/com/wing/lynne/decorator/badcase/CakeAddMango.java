package com.wing.lynne.decorator.badcase;

import java.math.BigDecimal;

public class CakeAddMango extends Cake{

    @Override
    public String getCakeMessage() {
        return super.getCakeMessage()+" 加芒果的";
    }

    @Override
    public BigDecimal getPrice() {
        return super.getPrice().add(new BigDecimal("10.42"));
    }
}
