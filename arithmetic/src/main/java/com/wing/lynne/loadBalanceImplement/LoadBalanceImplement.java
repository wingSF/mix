package com.wing.lynne.loadBalanceImplement;

public class LoadBalanceImplement {

    private static Service[] serviceIpList = new Service[3];

    public static void init() {
        serviceIpList[0] = new Service("ip1", "port1", 2);
        serviceIpList[1] = new Service("ip2", "port2", 1);
        serviceIpList[2] = new Service("ip3", "port3", 2);
    }


    //轮询
    public void polling() {

        int increment = 0;

        while (true) {

            Service service = serviceIpList[increment];

            increment++;

            if (increment == serviceIpList.length) {
                increment = 0;
            }

            service.service();

        }

    }

    //todo 权重轮询
    public void  pollingWithWeight(){



    }

    //todo 最小响应时间



    //还有最小链接需要对服务的资源进行监控，暂不实现
    //hash法，暂不实现


    static class Service {

        private String ip;
        private String port;
        private Integer weight;

        public Service(String ip, String port, Integer weight) {
            this.ip = ip;
            this.port = port;
            this.weight = weight;
        }

        public void service(){
            System.out.println(ip+":"+port+" service method invoke");
        }

        @Override
        public String toString() {
            return "Service{" +
                    "ip='" + ip + '\'' +
                    ", port='" + port + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }
}
