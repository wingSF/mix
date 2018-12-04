package com.wing.lynne.strategy.pay;

public class Order {

    private String userId;
    private String orderId;
    private double amount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PayResult pay(PayInterface payInterface){

        return payInterface.pay(this.userId,this.amount);
    }
}
