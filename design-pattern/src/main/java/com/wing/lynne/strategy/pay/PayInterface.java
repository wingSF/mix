package com.wing.lynne.strategy.pay;

//@FunctionalInterface
public interface PayInterface {

    PayResult pay(String uid, double amount);

//    todo java8对于lambda表达式的定义还需要补充，注释打开，PayTest类中的代码会报错
//    PayResult pay2(String uid);
}
