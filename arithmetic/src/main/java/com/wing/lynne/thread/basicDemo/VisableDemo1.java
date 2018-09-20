package com.wing.lynne.thread.basicDemo;

/**
 * 这个测试用例是在探索阶段的迷茫的开始，如果证明可见性问题存在？？？
 * 最开始一直使用static变量，发现根本没有可见性问题
 * <p>是真的没有可见性问题么，直到多日之后才发现</>
 * 如果使用非static变量，如果改变的时候，需要用final标识，语法上无法通过
 */
public class VisableDemo1 {

    private static boolean stop = false;

    /**
     * main线程对标识位的修改，子线程获取不到
     *
     * @param args
     * @throws InterruptedException
     */
//    public static void main(String[] args) throws InterruptedException {
//
//        Thread thread = new Thread(() -> {
//
//            int i = 0;
//
//            while (!stop) {
//                i++;
//            }
//            System.out.println("i = " + i);
//        });
//
//        thread.start();
//
//        /*----------- 一定要注意这句话 --------------*/
//        TimeUnit.SECONDS.sleep(1);
//        /*----------- 写和不写差距很大 --------------*/
//
//        stop = true;
//
//    }

    /**
     * 子线程的修改对于main线程是可见的
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {

            stop = true;
            while (true) {
                System.out.println("son thread running");
            }
        });

        thread.start();

        while (!stop) {
            System.out.println("main Thread running");
        }

        System.out.println("main Thread finished");

    }

}
