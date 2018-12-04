package com.wing.lynne.strategy.pay;

public class PayTest {

    public static void main(String[] args) {

        Order order = new Order();

        order.setAmount(128.5);
        order.setUserId("123");
        order.setOrderId("456");

        order.pay(new PayInterface() {
            @Override
            public PayResult pay(String uid, double amount) {
                System.out.println("xx 支付公司");
                System.out.println(uid + "正在支付");
                System.out.println("扣款" + amount);
                return new PayResult(200, "支付成功", amount);
            }
        });

        order.pay((uid, amount) -> {
            System.out.println("xx 支付公司");
            System.out.println(uid + "正在支付");
            System.out.println("扣款" + amount);
            return new PayResult(200, "支付成功", amount);
        });

    }
}
