package com.wing.lynne.prototype;

public class PojoParent implements Cloneable {

    private Pojo pojo;

    public Pojo getPojo() {
        return pojo;
    }

    public void setPojo(Pojo pojo) {
        this.pojo = pojo;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {

        PojoParent clone = (PojoParent)super.clone();

        clone.setPojo((Pojo) clone.getPojo().clone());

        return clone;
    }
}
