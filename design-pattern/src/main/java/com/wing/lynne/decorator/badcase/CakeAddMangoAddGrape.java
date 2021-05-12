package com.wing.lynne.decorator.badcase;

import java.math.BigDecimal;

public class CakeAddMangoAddGrape extends CakeAddMango{

    @Override
    public String getCakeMessage() {
        return super.getCakeMessage()+" 加葡萄的";
    }

    @Override
    public BigDecimal getPrice() {
        return super.getPrice().add(new BigDecimal("10.43"));
    }
}
