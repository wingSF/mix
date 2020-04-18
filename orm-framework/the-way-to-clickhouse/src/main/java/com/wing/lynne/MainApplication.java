package com.wing.lynne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * 官网地址
 * https://clickhouse.yandex/
 */
public class MainApplication {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("cc.blynk.clickhouse.ClickHouseDriver");
        Connection connection = DriverManager.getConnection("jdbc:clickhouse://${}:${port}/${dbname}","${username}","${password}");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("${sql}");
        while (resultSet.next()) {
            String string = resultSet.getString(1);
            System.out.println(string);
            string = resultSet.getString(2);
            System.out.println(string);
            string = resultSet.getString(3);
            System.out.println(string);
            string = resultSet.getString(4);
            System.out.println(string);
            string = resultSet.getString(5);
            System.out.println(string);
            string = resultSet.getString(6);
            System.out.println(string);

            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}
