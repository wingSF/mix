package com.wing.lynne.decorator.badcase;

import java.math.BigDecimal;

public class Cake {

    public String getCakeMessage(){
        return "最基础的Cake";
    }

    public BigDecimal getPrice(){
        return new BigDecimal("5.12");
    }
}
