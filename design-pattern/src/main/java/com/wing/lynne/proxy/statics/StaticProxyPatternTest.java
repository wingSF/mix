package com.wing.lynne.proxy.statics;

/**
 * 代理实现方式，Proxy必须持有Target的引用
 *
 * 静态代理中，Proxy代码硬编码，只能代理Target的单个行为，扩展性很差
 */
public class StaticProxyPatternTest {

    public static void main(String[] args) {

        Target target = new Target();

        Proxy proxy = new Proxy(target);

        proxy.invoke();

    }

}
