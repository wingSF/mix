package com.wing.lynne.prototype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pojo implements Cloneable, Serializable {

    private String name;
    private List list = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
