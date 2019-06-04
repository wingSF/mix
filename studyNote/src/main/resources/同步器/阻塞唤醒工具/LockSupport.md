# LockSupport
* 本文目前没有任何作用，只能了解个表面，更多需要深读jvm源码
* 是一个工具类，用来阻塞和唤醒线程
* 核心方法
    * park()/unpark(Thread thread)
    * parkNanos(long nanos)/parkUntil(long deadline)
* 实现原理
    * 都使用UNSAFE类的park(boolean isAbsolute,long time)
    * 该方法需要阅读jvm源码来深入理解
* 暂时理解
    * park带有Object参数的方法，为了再跟踪线程时，有更多的信息
    * 是Thread类resume()&suspend()方法的一种替换
    