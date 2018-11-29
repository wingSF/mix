package com.wing.lynne.fatory.factoryMethod;

import com.wing.lynne.fatory.Milk;
import com.wing.lynne.fatory.Telunsu;

public class TelunsunFactory implements MilkFactory {

    public Milk getMilk() {
        return new Telunsu();
    }
}
