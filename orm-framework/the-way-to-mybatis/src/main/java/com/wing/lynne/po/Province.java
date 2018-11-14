package com.wing.lynne.po;

public enum Province {

    SX("山西", 1),
    BJ("北京", 2),
    SX1("陕西", 3);

    private String provinceChineseName;
    private int privinceCode;

    Province(String address, int i) {
        this.provinceChineseName = address;
        this.privinceCode = i;
    }

    public String getProvinceChineseName() {
        return provinceChineseName;
    }

    public void setProvinceChineseName(String provinceChineseName) {
        this.provinceChineseName = provinceChineseName;
    }

    public int getPrivinceCode() {
        return privinceCode;
    }

    public void setPrivinceCode(int privinceCode) {
        this.privinceCode = privinceCode;
    }
}
