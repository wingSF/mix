package com.wing.lynne.assign;

import java.util.Map;

public class Leader {

    public Map<String, Worker> workerMap;

    public Leader(Map<String, Worker> workerMap) {
        this.workerMap = workerMap;
    }

    public void work(String orderCmd) {
        workerMap.get(orderCmd).work(orderCmd);
    }

}
