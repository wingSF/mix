package com.wing.lynne.assign;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.util.HashMap;
import java.util.Map;

public class AssignTest {

    public static void main(String[] args) {

        String orderCmd1 = "画页面";
        String orderCmd2 = "写代码";
        String orderCmd3 = "部署服务";

        Worker worker1 = new Worker("画页面");
        Worker worker2 = new Worker("写代码");
        Worker worker3 = new Worker("部署服务");

        Map<String,Worker> workerMap = new HashMap<>();
        workerMap.put(orderCmd1,worker1);
        workerMap.put(orderCmd2,worker2);
        workerMap.put(orderCmd3,worker3);

        Leader leader = new Leader(workerMap);

        Boss boss = new Boss(leader);

        boss.sendOrder(orderCmd1);
        boss.sendOrder(orderCmd2);
        boss.sendOrder(orderCmd3);
    }
}
