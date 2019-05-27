# 常见命令
> 对自己说，不熟悉的命令，一定先复制文件，再操作，等确认无误的时候，再做修改，切记切记，不急不躁，稳住稳住
* who
    * 提供给管理员查询当前服务器上登录的用户，包括登录时间/终端类型(tty1-7)/登录方式
* ps
    * 查看进程，常见参数aux|j|e|f
* jobs
    * 用于查看当前终端内任务
* command file &
    * 上述&符号，会启动后台运行该命令
* ctrl+z会暂停任务，任务进入后台模式
    * ctrl+c是终止任务
* bg 作业编号
    * 后台继续运行job
* fg 作业编号
    * 前台运行job
* file filename
    * 用于查看文件类型
* 环境变量
    * export path=$PATH;new pwd
    * 可以编辑~/.bashrc或者/etc/profile
        * bashrc是用户级别的修改
        * etc/profile是全局修改
    * 通过source|. 可以生效
* kill signnum pid
    * 命令本身不是杀死的意思，而是向某个pid发送一个信号
    * kill -l 用来查看kill列表
* cd -
    * 可以实现俩个目录之间来回切换，通过env属性中的oldpwd实现
* useradd
    * -g 指定分组
    * -d 指定目录
    * -s 指定shell
    * -m 注释
    * 必须是root用户
* passwd
    * 设置密码
* userdel
    * -r 递归删除
* echo
    * 用于回显数据
    * 设置hello=world  echo hello || echo $hello
    * echo $? 可以获取上个可执行程序的返回结果，即return值
* env
    * 展示当前shell的属性
* history
    * 用于查询历史输入的命令
    > 在e福州项目中，鸡贼的奥哥通过这个命令找到了，数据库的用户名密码
* 上翻/下翻/向前/向后/行首/行末/删除光标后
    * ctrl+p(previous)
    * ctrl+n(next)
    * ctrl+b(before)
    * ctrl+f(aFter)
    * ctrl+a(first letter ???)
    * ctrl+e(end)
    * ctrl+d(delete)
* cd(change dir)
    * .表示当前目录
    * ..表示父目录
* which 
    * 可以用于查看命令位置  
* rmdir
    * 删除只有.和..的目录
* mkdir
    * 创建文件夹
    * -p 递归创建 mkdir -p aaa/bbb/ccc
* touch
    * 用于创建一个空文件
    * 用于更新某个文件的操作时间
* mv 
    * 重命名文件
    * 移动文件
* cat/more/less
    * cat 查看
    * more的翻页速度比less的要快
* ls 
    * -a (all),查看所有文件，包括隐藏文件
* tree
    * 需要安装插件
    * 可以很清晰的查看目录内部的结构
    * sudo apt-get install tree
* head
    * 查看一个文件的前几行
    * head -10 pom.xml
* tail 
    * 查看文件的末尾 
* ln
    * 创建符号链接
    * -s参数创建的是软连接
* wc
    * word count
    > 59467@DESKTOP-FB60KJ2 MINGW64 /c/wing/myWorkspace/mix (master)  
    $ wc pom.xml  
    25  27 951 pom.xml
    * 上述中25表示文件的行数，27表示单词数，951表示字节数
* od
    * od -tcx filename
    * 查看文件的十六进制表达
* chmod
    * 修改文件权限
    * chmod 777 filename
    * chmod a+x|g-x|u+w|o-x
    > 1.注意777，其实是一个八进制的表达方式，实际是-0777  
    2.使用范围，所有使用者
* chown
    * 修改文件的所有者，root权限才能执行
    * chown 用户名:组名 文件名
* df
    * 查看磁盘空间
* du
    * 查看文件大小
* find
    * 查找文件
    * find 目录地址 -name  文件名称
    > 59467@DESKTOP-FB60KJ2 MINGW64 ~/Desktop  
    $ find . -name config*  
    ./config.txt
* grep
    * 内容查找
    * grep [option] pattern [File...]
    * option
        * -r:递归
        * -n:显示行号
        * -c:显示出现次数
    > grep "11111" ./* -r  
    ./config.txt:11111  
    Binary file ./local-parquet-reader-1.0.0.jar matches
* mount
    * 挂在/dev下的某个块设备到/mnt,一般使用/mnt作为挂载点
    > 因为新插入的u盘是块设备，不能cd进入进行操作，只能通过挂载到某个目录下，进行操作
    * sudo fdisk -l
        * 用于获取新插入设备的名称
    * sudo mount /dev/${新设备名称} /mnt
    * sudo umount /mnt
        * 主要要退出
* dd
    * 注意不是vim下的dd删除，是一个命令行执行的命令，功能类似cp
    * dd if=sourceFile of=targetFile
    > if是input file的意思|of是output file的意思
    * 常用于光盘资源拷贝，普通cp拷贝的结果是文件，dd拷贝的结果是.iso文件   
* tar
    * tar z|j c|x vf    目标文件名  源文件或目录名称
    * z是tar.gz | j是tar.bz2 ，没有这俩个选项只归档，不压缩
    * c是创建压缩包 | x是解压缩
    * 解压缩的时候，-C可以指定解压缩目录
* rar
    * rar a -r 目标文件名 源文件或目录名称
    * unrar x 压缩包
* zip
    * zip target original
    * unzip target
* gzip|zcat
    * gzip -h，查看帮助
    * gzip file file.gz
    * gzip -d file.gz
    * zcat file.gz > file
* ifconfig('I'nter'F'ace config)
    * 可以用来查看网络接口信息
    * sudo ifconfig eth0(网卡名称) down
    * sudo ifconfig eth0 up
    * sudo ifconfig eth0 ip
        * 用于临时修改计算机中的ip地址，重启网卡失效
* ping
    * 用于检测是否与目标服务器网络畅通
    * 参数很多，但实际很多服务器都会禁止ping，所以要学会用telnet命令
* umask
    * 文件权限掩码
    * 提供给管理员的统一控制文件的权限
    * umask -S可以以字符形式查看文件权限
* alias
    * 命令别名
    * 可以实现将rm命令改成mv
    * ex:alias rm="rm -i"
* ssh
    * ssh root@ip用于远程登录
    * 加密传输
    * 对比telnet是明文传输
* man
    * man man(查看帮助)
        * 第一章 shell命令
        * 第二章 系统调用
        * 第三章 库函数
    * man 2(第二章)  method_name(函数名称)
    * man -k method_name(函数名称) 模糊查找函数
* uname -a
    * 查看内核版本信息
* lsb_release -a
    * 查看发行版本
* clear
    * 清屏
    * ctrl+l
* date
    * 查看时间
* 有些危险的命令
    * poweroff
    * shutdown -t 秒数
    * reboot
* 源码安装过程
    * ./configure
        * 环境检查等
    * make
        * 编译源码
    * sudo make install
        * 安装
    * sudo make distclean
        * 卸载
* gcc
    * -v 用来查看gcc版本
    * -I 指定头文件目录
        * <>和""引入头文件的区别，""会先搜索，再搜索类库
    * -c 只生成.0，不进行链接
    * -g 带有调试信息，gdb可以获取源码
    * -Wall 提示更多警告信息
    * -E 生成预处理文件
    * -o 编译优化级别 0-3
