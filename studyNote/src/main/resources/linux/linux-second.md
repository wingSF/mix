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
    