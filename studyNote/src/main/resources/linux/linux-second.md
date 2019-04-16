# 常见命令
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