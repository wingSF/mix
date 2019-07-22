package com.wing.lynne;

import java.math.BigDecimal;

public class BigDemicalDemo {

    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal("0.3");
        System.out.println(b1);
        BigDecimal b2 = new BigDecimal("7");
        System.out.println(b2);
        BigDecimal result = b1.divide(b2,4, BigDecimal.ROUND_HALF_UP);
        System.out.println(result);
    }
}
