package com.wing.lynne.decorator.goodcase;

import java.math.BigDecimal;

public class BaseCake extends Cake {
    @Override
    public String getCakeMessage() {
        return "base cake";
    }

    @Override
    public BigDecimal getPrice() {
        return new BigDecimal("5.12");
    }
}
