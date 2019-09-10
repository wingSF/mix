# Thread
* what is thread
    * 现代操作系统运行一个程序时，都会为其创建一个进程。例如启动一个java程序，会创建一个java进程。
    * 现代操作系统调用的最小单元是线程，也叫轻量级进程，在一个进程里面可以创建多个线程，每个线程都有独立的程序计数器，堆栈和局部变量，能访问共享的内存变量
    * 处理器在多个线程之间高速切换，给人感觉线程在同时执行
* why use thread
    * 随着核心数的增多，超线程技术的广泛应用，并行计算已经成为趋势
    * 操作系统调度的最小单位是线程，且一个线程只能在一个核心上运行，通过将单个线程内的任务，拆分给多个核心运行，提升了效率，显著减少程序运行时间
* 线程优先级
    * 线程调度采用时分形式，即操作系统会分出一个个时间片，线程被分配到若干个时间片执行，时间片用完了就会发生线程调度
    * 时间片分配的多，占用资源就多
    * 优先级高的分配的时间片较多，低的较少，但是具体实现决定于操作系统。
    * io操作或者阻塞多的线程，需要高优先级，多分配时间片，因为io操作会让出执行权，多分配时间片，可以减少等待的时间
    * 计算密集型的设置低优先级，少分配时间片，保证cpu不会出现，很长一段时间片都在执行同一个线程，这样的话别的线程等待太久了    

* 线程状态
    * NEW           新建Thread，没有调用start()方法之前
    * RUNNABLE      
        * RUNNING   运行中，正在被执行
        * READY     就绪，等待系统调用
    * BLOCKED       阻塞状态，等待锁资源
    * WAITING       等待状态，等待别的线程的通知，
    * TIME_WAITING  超时等待状态，等待时间，到时自动返回
    * TERMINATED    终结状态
* Daemon线程
    * 当所有的线程都是Daemon线程，JVM自动退出
    * 创建线程的时候，可以指定是否是Daemon线程
    * 在Daemon线程中，finally不保证一定执行
    
* 线程创建
    * a线程内部创建了b线程，那么a是b的父线程
    * 建议自行创建的线程一定要设置名称
    * 大部分属性获取自父线程，是否Daemon/优先级/contextClassLoader/ThreadLocal等

* 线程启动
    * start方法启动
    * 会调用run方法
    
* 线程中断
    * 是一个标志位，表示运行中的线程是否被其它线程进行了中断操作，其它线程调用该线程的interrupt方法即可
    * 线程通过检查自身是否被中断来进行响应，通过调用isInterrupt方法判断是否被中断，也可以使用Thread.interrupted()对标志位进行复位操作
    * 如果线程已经终结，isInterrupt返回false
    * 当线程在执行会抛出interruptedException的方法时，被别的线程调用了interrupt方法，此时会先将标识位复位，再抛出异常
    * 核心方法整理
        * interrupt()
            * 当前线程调用自己的interrupt方法来中断线程是允许的，否则调用权限查询是否有权限
            * 等待在某些wait方法上，调用会清除中断状态，并且给一个中断异常
            * 等待在某些io方法上，会设置中断状态，并给一个ClosedByInterruptException
            * 等待在selector上，会设置中断状态，并立即返回
            * 如果是等待在其他方法，会设置中断状态
        * interrupted()
            * currentThread().isInterrupted(true);
        * isInterrupted()
            * isInterrupted(false);
        * native boolean isInterrupted(boolean ClearInterrupted)
            * 返回检测线程是否被中断了
            * 如果参数为true，中断状态会被清除
        * native void interrupt0()
            * native方法用来帮助中断
* suspend/resume/stop
    * 不推荐使用的方法
    * 因为不会释放资源，容易发生死锁（suspend/stop）

* 正确的线程终止方式
    * 通过interrupt或者volatile标识位的方式，结束线程
    
* 线程通信
    * 线程就像一个已经编写好的脚本一样，一经运行，就执行自己的任务
    * 如果线程只关注自己的任务，则价值会比较小，相比较，如果线程之间能通信，则可以配合起来共同完成
    * 通过volatile或者synchronized实现
        * volatile能够保证读写是原子操作，且操作结果刷新回主内存/从内存获取
            * 不在非必要场景使用volatile，会降低性能
        * synchronized通过排他，保证临界区中只有一个线程在运行
            * 同步代码块，使用moniterenter和moniterexit俩个指令实现
            * 同步方法，使用ACC_SYNCHRONIZED来实现
            * 以上俩种，本质上都是获取对象的moniter，要想访问被moniter保护的Object对象，必须先获取moniter，如果失败，进入等待队列，等待前一个线程释放moniter，此时等待线程为block状态。持有线程释放后，会唤醒等待线程。   

* 等待/通知
    * 假设一个场景，A线程会不停的改变一个共享变量，B线程监视该共享变量，一旦满足某个条件，就作出某种反应。
        * 这个场景下，B需要循环执行判断，如果不加等待，会导致无效的空转，如果加等待，则无法保证及时性。
        * 使用wait/notify机制解决
    * 使用wait/notify/notifyall之前，必须获取object moniter
    * 调用wait之后，线程由RUNNABLE状态变成WAITING，并且释放获取的锁，进入等待队列
    * 调用notify之后，不会立马唤醒等待的线程，必须要等到锁释放之后，才会唤醒
    * 唤醒的线程由WAITING状态变成BLOCK状态，进入同步队列
    * 要想wait方法返回，必须获取到锁对象
    
* 管道输入/输出流
    * 不同于文件/网络的输入输出，管道主要用于线程之间传输，介质是内存
    * 主要实现 PipedOutputStream/PipedInputStream PipedWriter/PipedReader
    * 想不到有什么妙用，暂时//todo 

* join
    * 等待调用join线程的终结，也可以加入等待超时时间
    * join的实质是，wait在线程对象上，等待线程终结的时候，notifyall

* ThreadLocal
    * todo 后续补充，很重要的东西，但是介绍很少
    
* 等待超时模式
    * 当前时间a，超时时间t，一直等待，知道经过了t时间之后，再返回
    
* 线程池模型
    * 调用execute方法向线程池提交任务
    * 线程池提供了增加/减少worker的方法
    * 任务队列也是一个核心关注点，不同的任务队列，也将是不同的效果和现象
    * 当队列为空时，worker wait在该对象上，当任务添加的时候，notify唤醒一个worker，而不是notifyAll
    
             
    