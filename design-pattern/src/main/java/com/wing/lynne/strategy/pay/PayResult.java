package com.wing.lynne.strategy.pay;

public class PayResult {

    private int code;
    private String message;
    private double amount;

    public PayResult() {
    }

    public PayResult(int code, String message, double amount) {
        this.code = code;
        this.message = message;
        this.amount = amount;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
