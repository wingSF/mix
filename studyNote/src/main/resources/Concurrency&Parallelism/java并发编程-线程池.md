# 线程池
* ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,timeUnit,workQueue)
    * 参数讲解
        * corePoolSize 核心线程数
        * maximumPoolSize 最大线程数
        * keepAliveTime 线程无任务时的等待时间
        * timeUnit 等待时间
        * workQueue 任务队列
    * 核心方法
        * newFixedThreadPool
            * (n,n,0,MILLISECONDS,new LinkedBlockingQueue()）
        * newSingleThreadExecutor
            * (1,1,0,MILLISECONDS,new LinkedBlockingQueue())
        * newCachedThreadPool
            * (0,Integer.MAX_VALUE,60L,SECONDS,new SynchronousQueue())
        > new LinkedBlockingQueue()--> capacity = Integer.MAX_VALUE
        new SynchronousQueue()  --> 特殊队列，最多只有一个元素存在，存在多个线程等待

# 问题
* Java线程池既然是线程池，他是如何复用创建的线程的。(from 奥哥...)
    * //todo

        