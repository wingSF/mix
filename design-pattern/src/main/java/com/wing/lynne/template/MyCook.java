package com.wing.lynne.template;

public class MyCook {

    private Cook cook = new Cook();

    public void makeFood() {
        cook.makeFood(() -> {
            System.out.println("1洗菜");
            System.out.println("2放油");
            System.out.println("3炒");
            System.out.println("4醋&酱油");
            System.out.println("5盐");
            System.out.println("6出锅");
        }, () -> {
            System.out.println("选新鲜的食材");
            System.out.println("选便宜的食材");
        });
    }
}
