package com.wing.lynne.template;

public class Cook {

    public void makeFood(CookProcess cookProcess,Choose choose) {
        this.prepareMaterial(choose);
        this.doing(cookProcess);
        this.clean();
    }

    public void clean() {
        System.out.println("收拾残局");
    }

    public void doing(CookProcess cookProcess) {
        System.out.println("------开始做饭-----------");
        cookProcess.process();
        System.out.println("------饭做完了，开吃-----------");
    }

    public void prepareMaterial(Choose choose){
        System.out.println("------准备材料阶段-----------");
        choose.choose();
        System.out.println("------材料准备完毕-----------");
    }


}
