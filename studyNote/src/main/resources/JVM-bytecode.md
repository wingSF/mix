# 字节码
> 缘起于奥哥提问  
Q1:在java中，try catch finally中如何实现，保证来finally一定会被执行  
A1:[认真读此文慢慢体会](http://blog.jamesdbloom.com/JavaCodeToByteCode_PartTwo.html#try_catch_finally)，如果可以自己写一段代码，javap -v查看class文件(不是.java)  
Q2:奥哥又说要在try里面return，catch里面return，一顿操作猛如虎  
A2:认真学好字节码，此处暂无特别好的推荐，先开始最基础的

## const系列
* 作用:将简单类型的数值数据推送到栈顶，只适用于 -1(用m表示)，0，1，2，3，4，5
> iconst 操作int型  
lconst 操作long型  
fconst 操作float型  
dconst 操作double型
* 例子
    * iconst_1  
    ⚠️上述的iconst_1中的1不能认为是操作数，整体是一个指令
    
## push系列
* 作用:将正数类型的数值数据推送到栈顶，命令后可接一个参数
* bipush:适用于单字节(-128,127)
* sipush:适用于双字节(-32768,32767)

## ldc系列
* 作用:将数值常量于String常量从常量池推送到栈顶，命令后可接一个参数，表示参数在常量池中的位置
* 什么数据在常量池
    * 在const系列和push系列操作数范围以外的数值类型常量
    * 所有不是通过new创建的String都在常量池中
* ldc&ldc_w
    * 将int/float/String型常量推到栈顶，当操作数地址大于256时，使用ldc_w
* ldc2_w
    * 将long/double型常量推到栈顶
    
## load系列
* 作用:将本地变量(数值类型+引用类型)推送到栈顶
* iload|lload|fload|aload + _0|_1|_2|_3
    * 表示将本地变量表中的第*个*类型的本地变量推送到栈顶，a指引用类型
    * 访问第4个及4个以上的变量，需要在指令后面跟一个参数，该参数是本地变量的编号(不区分类型)
* aload(a应该是array吧，猜的，至少解释的通)，后面跟参数
    * iaload int型数组
    * laload long型数组
    * faload float型数组
    * daload double型数组
    * aaload 引用类型数组
    * baload byte或者boolean型数组
    * caload char型数组
    * saload short型数组
    
## store系列
* 作用:将栈顶的值存入到本地变量表中
* 使用方式于load完全一致，推理可得

## pop系列
* 作用:将栈顶的元素弹出
//todo
* pop/pop2/dup/dup_x1/dup_x2/dup2/dup2_x1/dup2_x2

## 位操作
//todo 一片
[资料](https://www.cnblogs.com/tenghoo/p/jvm_opcodejvm.html)

> 总结:要想明确的知道try catch finally语句块中的，各种变态return exception，最好看字节码  
ex: TryCatchExceptionDemo.java

## exception处理过程

* 方法源码
```java
public static void testMethod(ReentrantLock lock) throws InterruptedException {
        //            lock.lock();
        lock.lockInterruptibly();

        try {
            threadName = Thread.currentThread().getName();
        } finally {
            lock.unlock();
        }
    }
```
* 方法字节码
```java
public static void testMethod(java.util.concurrent.locks.ReentrantLock) throws java.lang.InterruptedException;
    descriptor: (Ljava/util/concurrent/locks/ReentrantLock;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=2, args_size=1
         0: aload_0
         1: invokevirtual #15                 // Method java/util/concurrent/locks/ReentrantLock.lockInterruptibly:()V
         4: invokestatic  #16                 // Method java/lang/Thread.currentThread:()Ljava/lang/Thread;
         7: invokevirtual #17                 // Method java/lang/Thread.getName:()Ljava/lang/String;
        10: putstatic     #18                 // Field threadName:Ljava/lang/String;
        13: aload_0
        14: invokevirtual #19                 // Method java/util/concurrent/locks/ReentrantLock.unlock:()V
        17: goto          27
        20: astore_1
        21: aload_0
        22: invokevirtual #19                 // Method java/util/concurrent/locks/ReentrantLock.unlock:()V
        25: aload_1
        26: athrow
        27: return
      Exception table:
         from    to  target type
             4    13    20   any
      LineNumberTable:
        line 30: 0
        line 33: 4
        line 35: 13
        line 36: 17
        line 35: 20
        line 36: 25
        line 37: 27
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      28     0  lock   Ljava/util/concurrent/locks/ReentrantLock;
      StackMapTable: number_of_entries = 2
        frame_type = 84 /* same_locals_1_stack_item */
          stack = [ class java/lang/Throwable ]
        frame_type = 6 /* same */
    Exceptions:
      throws java.lang.InterruptedException
}
```
* 简单介绍(本次只按照代码实际逻辑分析字节码)
    * 0: aload_0
        * 从本地变量表把第0个对象，及lock对象推到栈顶
    * 1: invokevirtual #15                 // Method java/util/concurrent/locks/ReentrantLock.lockInterruptibly:()V
        * 对应代码lock.lockInterruptibly();
    * 由于代码的控制，t1线程会被外部中断，后续会抛出InterruptException(中断异常)
    * 根据异常的类型，去 Exception table中根据from to type定位异常的处理指令为20
    * 20: astore_1
        * 将异常对象保存到本地变量表的第1个位置(第0个后面的那个)
    * 21: aload_0
        * 将本地变量表中的第0个对象推到栈顶(lock对象)
    * 22: invokevirtual #19                 // Method java/util/concurrent/locks/ReentrantLock.unlock:()V
        * 调用lock的unlock方法
    * 25: aload_1
        * 加载本地变量表的第1个对象(异常对象)
    * 26: athrow  
        * 抛出异常对象，方法执行结束
        
