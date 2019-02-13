package com.wing.lynne.thread.connectionPool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialize) {
        if (initialize > 0) {
            for (int i = 0; i < initialize; i++) {
                pool.add(ConnectionDriver.createConnection());
            }
        } else {
            throw new RuntimeException();
        }
    }

    public Connection getConnection(long miles) throws InterruptedException {

        synchronized (pool) {

            if (miles <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {

                long end = System.currentTimeMillis() + miles;
                long remain = miles;

                while (pool.isEmpty() && remain > 0) {
                    pool.wait(remain);
                    remain = end - System.currentTimeMillis();
                }

                Connection result = null;

                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }

                return result;
            }

        }
    }

    public Boolean releaseConnection(Connection connection) {

        if (connection != null) {

            synchronized (pool) {

                pool.addLast(connection);
                pool.notifyAll();

            }

        }
        return true;
    }

}
