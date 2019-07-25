# docker
## 命令
* docker ps
    * 查看当前container运行情况，可以获取id和name
* docker logs id|name
    * 查看日志
    * 增加-f参数，可以达到tailf的效果
* docker stop id|name
    * 停止服务
* docker rm id|name
    * 删除contanier
* docker restart id|name
    * 可以对正在运行的container进行重启
* docker start id|name
    * 启动容器，此时参数是不能调整的，在容器启动前已经指定
* docker images
    * 查看当前有哪些镜像
* docker run 镜像名称|镜像id
    * 运行某个镜像
    * -i 与容器内的stdin连通
    * -t 指定一个终端
    * -d 后台运行
    * -P 将内部的网络端口映射到我们使用的主机上
        * -P 5000:5000 前面是主机端口，后面是容器端口
        * 可以通过docker port 查看 
* docker top id|name
    * 可以查看一些基本的信息
* docker pull 镜像名称
    * 获取镜像
* docker search 镜像名称
    * 搜索镜像
